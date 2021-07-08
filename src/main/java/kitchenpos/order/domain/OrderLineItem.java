package kitchenpos.order.domain;

import kitchenpos.menu.domain.Menu;
import kitchenpos.order.dto.OrderLineItemRequest;

import javax.persistence.*;

@Entity
public class OrderLineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private long quantity;

    public OrderLineItem() {}

    public OrderLineItem(Long seq, Order order, Menu menu, long quantity) {
        this.seq = seq;
        this.order = order;
        this.menu = menu;
        this.quantity = quantity;
    }

    public Long getSeq() {
        return seq;
    }

    public Order getOrder() {
        return order;
    }

    public Long getMenuId() {
        return menu.getId();
    }

    public Menu getMenu() {
        return menu;
    }

    public long getQuantity() {
        return quantity;
    }

    public void updateQuantity(final long quantity) {
        this.quantity = quantity;
    }

//    public static OrderLineItem to(OrderLineItemRequest request) {
//        return new OrderLineItem(request.getSeq(), request.getOrderId(), request.getMenuId(), request.getQuantity());
//    }
}
