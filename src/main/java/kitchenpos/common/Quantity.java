package kitchenpos.common;

import java.util.Objects;

public class Quantity {
    private final Long amount;

    public Quantity() {
        this.amount = 0L;
    }

    public Quantity(Long amount) {
        checkNegative(amount);
        this.amount = amount;
    }

    public Long amount() {
        return amount;
    }

    private void checkNegative(Long amount) {
        if (amount < 0L) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quantity quantity = (Quantity) o;
        return Objects.equals(amount, quantity.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
