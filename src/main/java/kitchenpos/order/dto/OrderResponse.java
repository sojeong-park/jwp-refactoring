package kitchenpos.order.dto;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderLineItem;

import java.util.List;

public class OrderResponse {
    private Long id;
    private OrderTableResponse orderTable;
    private String orderStatus;
    private List<OrderLineItem> orderLineItems;

    public OrderResponse() {}

    public OrderResponse(Order order) {
        this.id = order.getId();
        this.orderTable = OrderTableResponse.of(order.getOrderTable());
        this.orderStatus = order.getOrderStatus().name();
        this.orderLineItems = order.getOrderLineItems();
    }

    public Long getId() {
        return id;
    }

    public OrderTableResponse getOrderTable() {
        return orderTable;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }

    public static OrderResponse of(Order order) {
        return new OrderResponse(order);
    }
}
