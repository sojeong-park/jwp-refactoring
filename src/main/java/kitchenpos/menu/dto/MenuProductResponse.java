package kitchenpos.menu.dto;

import kitchenpos.menu.domain.MenuProduct;

public class MenuProductResponse {
    private Long seq;
    private Long productId;
    private long quantity;

    public MenuProductResponse() {}

    public MenuProductResponse(MenuProduct menuProduct) {
        this.seq = menuProduct.getSeq();
        this.productId = menuProduct.getProductId();
        this.quantity = menuProduct.getQuantity();
    }

    public static MenuProductResponse of(MenuProduct menuProduct) {
        return new MenuProductResponse(menuProduct);
    }
}
