package kitchenpos.order.domain;

import kitchenpos.common.NumberOfGuests;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class OrderTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "table_group_id")
    private TableGroup tableGroup;

    private NumberOfGuests numberOfGuests;
    private boolean empty;

    public OrderTable() {}

    public OrderTable(Long id, int numberOfGuests, boolean empty) {
        this.id = id;
        this.numberOfGuests = new NumberOfGuests(numberOfGuests);
        this.empty = empty;
    }

    public OrderTable(int numberOfGuests, boolean empty) {
        this.tableGroup = null;
        this.numberOfGuests = new NumberOfGuests(numberOfGuests);
        this.empty = empty;
    }

    public OrderTable(Long id, TableGroup tableGroup, int numberOfGuests, boolean empty) {
        this.id = id;
        this.tableGroup = tableGroup;
        this.numberOfGuests = new NumberOfGuests(numberOfGuests);
        this.empty = empty;
    }

    public Long getId() {
        return id;
    }

    public TableGroup getTableGroup() {
        return tableGroup;
    }

    public Long getTableGroupId() {
        return tableGroup.getId();
    }

//    public void updateTableGroupId(final Long tableGroupId) {
//        this.tableGroupId = tableGroupId;
//    }

    public int getNumberOfGuests() {
        return numberOfGuests.number();
    }

    public void updateNumberOfGuests(final int numberOfGuests) {
        if (numberOfGuests < 0) {
            throw new IllegalArgumentException();
        }
        if (getEmpty()) {
            throw new IllegalArgumentException();
        }
        this.numberOfGuests = new NumberOfGuests(numberOfGuests);
    }

    public boolean getEmpty() {
        return empty;
    }

    public void updateEmpty(final boolean empty) {
        if (Objects.nonNull(getTableGroupId())) {
            throw new IllegalArgumentException();
        }
        this.empty = empty;
    }

    public void validRegisterOrderTable() {
        if (!getEmpty() || Objects.nonNull(getTableGroupId())) {
            throw new IllegalArgumentException();
        }
    }

    public void unGroup() {
        this.tableGroup = null;
    }
}
