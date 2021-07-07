package kitchenpos.menu.application;

import kitchenpos.menu.domain.*;
import kitchenpos.menu.dto.MenuRequest;
import kitchenpos.menu.dto.MenuResponse;
import kitchenpos.product.domain.Product;
import kitchenpos.product.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private ProductRepository productRepository;
    private MenuRepository menuRepository;
    private MenuGroupRepository menuGroupRepository;

    public MenuService(
            final ProductRepository productRepository,
            final MenuRepository menuRepository,
            final MenuGroupRepository menuGroupRepository
    ) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
    }

    @Transactional
    public MenuResponse create(final MenuRequest request) {
        hasMenuProducts(request.getMenuProducts());
        MenuGroup menuGroup = menuGroupRepository.findById(request.getMenuGroupId()).orElseThrow(IllegalArgumentException::new);
        Menu createdMenu = menuRepository.save(new Menu(request.getName(), request.getPrice(), menuGroup, request.getMenuProducts()));
        return MenuResponse.of(createdMenu);
    }

    private void hasMenuProducts(List<MenuProduct> menuProducts) {
        for (final MenuProduct menuProduct : menuProducts) {
            final Product product = productRepository.findById(menuProduct.getProductId())
                    .orElseThrow(IllegalArgumentException::new);
        }
    }

    public List<MenuResponse> list() {
        final List<Menu> menus = menuRepository.findAll();
        return menus.stream()
                .map(MenuResponse::of)
                .collect(Collectors.toList());
    }
}
