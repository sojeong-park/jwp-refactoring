package kitchenpos.order.domain;

import kitchenpos.common.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@AttributeOverride(name = "createdDate", column = @Column(name = "ORDERED_TIME"))
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_table_id")
    private OrderTable orderTable;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Embedded
    private OrderLineItems orderLineItems;

    public Order() {}

    public Order(Long id, OrderTable orderTable, OrderStatus orderStatus, List<OrderLineItem> orderLineItems) {
        this.id = id;
        this.orderTable = orderTable;
        this.orderStatus = orderStatus;
        this.orderLineItems = new OrderLineItems(orderLineItems);
        updateOrderStatus(orderStatus);
    }

    public Order(OrderTable orderTable, OrderStatus orderStatus) {
        this.id = id;
        this.orderTable = orderTable;
        this.orderStatus = orderStatus;
        updateOrderStatus(orderStatus);
    }

    public Long getId() {
        return id;
    }

    public OrderTable getOrderTable() {
        return orderTable;
    }

    public Long getOrderTableId() {
        return orderTable.getId();
    }

//    public void updateOrderTableId(final OrderTable orderTableId) {
//        this.orderTableId = orderTableId;
//    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void updateOrderStatus(final OrderStatus orderStatus) {
        validCompletionStatus(orderStatus);
        this.orderStatus = orderStatus;
    }

    public void validCompletionStatus(OrderStatus orderStatus) {
        if (orderStatus.equals(orderStatus)) {
            throw new IllegalArgumentException();
        }
//        if (Objects.equals(orderStatus, this.orderStatus)) {
//            throw new IllegalArgumentException();
//        }
    }

    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems.getOrderLineItems();
    }

//    public void updateOrderLineItems(final List<OrderLineItem> orderLineItems) {
//        this.orderLineItems = orderLineItems;
//    }
}
