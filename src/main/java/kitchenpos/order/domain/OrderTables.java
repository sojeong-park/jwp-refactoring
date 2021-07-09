package kitchenpos.order.domain;

import org.springframework.util.CollectionUtils;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.*;

@Embeddable
public class OrderTables {
    @OneToMany(mappedBy = "order", cascade = {CascadeType.ALL})
    private List<OrderTable> orderTables = new ArrayList<>();

    public OrderTables(List<OrderTable> orderTables) {
        validOrderTablesSize();
        validRegisterOrderTableList(orderTables);
        this.orderTables = orderTables;
    }

    public List<OrderTable> getOrderTables() {
        return orderTables;
    }

    private void validOrderTablesSize() {
        if (CollectionUtils.isEmpty(orderTables) || orderTables.size() < 2) {
            throw new IllegalArgumentException();
        }
    }

    private void validRegisterOrderTableList(List<OrderTable> orderTables) {
        for (final OrderTable orderTable : orderTables) {
            orderTable.validRegisterOrderTable();
        }
    }

    public void unGroup() {
        for (final OrderTable orderTable : orderTables) {
            orderTable.unGroup();
        }
    }
}
