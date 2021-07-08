package kitchenpos.order.dto;

import kitchenpos.order.domain.OrderTable;

public class OrderTableResponse {
    private Long id;
    private Long tableGroupId;
    private int numberOfGuests;
    private boolean empty;

    public OrderTableResponse() {}

    public OrderTableResponse(OrderTable orderTable) {
        this.id = orderTable.getId();
        this.tableGroupId = orderTable.getTableGroupId();
        this.numberOfGuests = orderTable.getNumberOfGuests();
        this.empty = orderTable.getEmpty();
    }

    public static OrderTableResponse of(OrderTable orderTable) {
        return new OrderTableResponse(orderTable);
    }
}
