package kitchenpos.product.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Price {
    private static final int ZERO = 0;
    private BigDecimal price;

    public Price(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < ZERO) {
            throw new IllegalArgumentException();
        }
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void updatePrice(BigDecimal price) {
        this.price = price;
    }
}
