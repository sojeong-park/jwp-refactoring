package kitchenpos.common;

public class NumberOfGuests {
    private final int number;

    public NumberOfGuests() {
        this.number = 0;
    }

    public NumberOfGuests(int number) {
        checkNegative(number);
        this.number = number;
    }

    private void checkNegative(int number) {
        if (number < 0) {
            throw new IllegalArgumentException();
        }
    }

    public int number() {
        return number;
    }
}
