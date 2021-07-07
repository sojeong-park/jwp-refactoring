package kitchenpos.menu.domain;

import kitchenpos.menu.dto.MenuProductRequest;
import kitchenpos.product.domain.Product;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class MenuProducts {
    @OneToMany(mappedBy = "menu", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<MenuProduct> menuProducts = new ArrayList<>();

    public MenuProducts(List<MenuProduct> menuProducts, BigDecimal price) {
        BigDecimal sum = calculateSum(menuProducts, price);
        isValidSum(sum, price);
        this.menuProducts = menuProducts;
    }

    private BigDecimal calculateSum(List<MenuProduct> menuProducts, BigDecimal price) {
        BigDecimal sum = BigDecimal.ZERO;
        for (final MenuProduct menuProduct : menuProducts) {
            sum = sum.add(price.multiply(BigDecimal.valueOf(menuProduct.getQuantity())));
        }
        return sum;
    }

    private void isValidSum(BigDecimal sum, BigDecimal price) {
        if (price.compareTo(sum) > 0) {
            throw new IllegalArgumentException();
        }
    }

    public void addMenuProducts(MenuProduct menuProduct) {
        menuProducts.add(menuProduct);
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }
}
