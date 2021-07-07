package kitchenpos.Product.domain;

import kitchenpos.product.domain.Name;
import kitchenpos.product.domain.Price;
import kitchenpos.product.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Product 도메인 테스트")
public class ProductTest {
    @Test
    @DisplayName("상품 등록 테스트")
    void create() {
        BigDecimal price = new BigDecimal(18000);
        Product product = new Product(new Name("뿌링클"), new Price(price));

        assertThat(product.getName()).isEqualTo("뿌링클");
        assertThat(product.getPrice()).isEqualTo(price);
    }

    @Test
    @DisplayName("상품 가격 예외 테스트")
    void priceException() {
        BigDecimal price = new BigDecimal(-1);

        assertThatThrownBy(() -> {
            new Product(new Name("뿌링클"), new Price(price));
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
