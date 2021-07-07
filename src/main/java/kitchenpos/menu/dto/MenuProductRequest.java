package kitchenpos.menu.dto;

import kitchenpos.menu.domain.MenuProduct;

import java.util.List;

public class MenuProductRequest {
    private Long seq;
    private Long productId;
    private long quantity;

    public MenuProductRequest() {}

    public MenuProductRequest(Long seq, Long productId, long quantity) {
        this.seq = seq;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getSeq() {
        return seq;
    }

    public Long getProductId() {
        return productId;
    }

    public long getQuantity() {
        return quantity;
    }

    public MenuProduct toMenuProduct() {
        return new MenuProduct(seq, productId, quantity);
    }
}
