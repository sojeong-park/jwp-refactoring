package kitchenpos.order.domain;

import kitchenpos.common.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class TableGroup extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private OrderTables orderTables;

    public TableGroup() {}

    public TableGroup(Long id, List<OrderTable> orderTables) {
        this.id = id;
        this.orderTables = new OrderTables(orderTables);
    }

    public Long getId() {
        return id;
    }

    public List<OrderTable> getOrderTables() {
        return orderTables.getOrderTables();
    }

    public void updateOrderTables(final List<OrderTable> orderTables) {
        this.orderTables = orderTables;
    }
}
