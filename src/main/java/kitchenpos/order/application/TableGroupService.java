package kitchenpos.order.application;

import kitchenpos.dao.OrderDao;
import kitchenpos.dao.OrderTableDao;
import kitchenpos.dao.TableGroupDao;
import kitchenpos.order.domain.OrderStatus;
import kitchenpos.order.domain.OrderTable;
import kitchenpos.order.domain.OrderTables;
import kitchenpos.order.domain.TableGroup;
import kitchenpos.order.domain.repository.OrderRepository;
import kitchenpos.order.domain.repository.OrderTableRepository;
import kitchenpos.order.domain.repository.TableGroupRepository;
import kitchenpos.order.dto.OrderTableRequest;
import kitchenpos.order.dto.TableGroupRequest;
import kitchenpos.order.dto.TableGroupResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TableGroupService {
    private final OrderRepository orderRepository;
    private final OrderTableRepository orderTableRepository;
    private final TableGroupRepository tableGroupRepository;

    public TableGroupService(final OrderRepository orderRepository, final OrderTableRepository orderTableRepository, final TableGroupRepository tableGroupRepository) {
        this.orderRepository = orderRepository;
        this.orderTableRepository = orderTableRepository;
        this.tableGroupRepository = tableGroupRepository;
    }

    @Transactional
    public TableGroupResponse create(final TableGroupRequest tableGroup) {
        final List<OrderTableRequest> orderTables = tableGroup.getOrderTables();

        final List<Long> orderTableIds = orderTables.stream()
                .map(OrderTableRequest::getId)
                .collect(Collectors.toList());

        final List<OrderTable> savedOrderTables = orderTableRepository.findAllById(orderTableIds);

        //2. 저장된 주문테이블과 그룹지정하려는 주문 테이블의 개수가 같지 않으면 오류 발생한다.
        if (orderTables.size() != savedOrderTables.size()) {
            throw new IllegalArgumentException();
        }

        final TableGroup tableGroup1 = new TableGroup(savedOrderTables);

        //1. orderTableRequest 사이즈가 2개 이상인지 비어있지는 않은지 검증한다.
//        if (CollectionUtils.isEmpty(orderTables) || orderTables.size() < 2) {
//            throw new IllegalArgumentException();
//        }

        //3. 모든 주문 테이블들이 빈테이블이 아니거나 groupid가 있다면(그룹지어져있다면) 단체로 묶을 수 없다.
//        for (final OrderTable savedOrderTable : savedOrderTables) {
//            if (!savedOrderTable.getEmpty() || Objects.nonNull(savedOrderTable.getTableGroupId())) {
//                throw new IllegalArgumentException();
//            }
//        }


        final TableGroup savedTableGroup = tableGroupRepository.save(tableGroup1);

//        final Long tableGroupId = savedTableGroup.getId();
//        for (final OrderTable savedOrderTable : savedOrderTables) {
//            savedOrderTable.updateTableGroupId(tableGroupId);
//            savedOrderTable.updateEmpty(false);
//            orderTableDao.save(savedOrderTable);
//        }
//        savedTableGroup.updateOrderTables(savedOrderTables);

        return TableGroupResponse.of(savedTableGroup);
    }

    @Transactional
    public void ungroup(final Long tableGroupId) {
        //tableGroup 에 등록되어있는 orderTable을 해제한다.
        //그러면 orderTable은? 같이 해제가 되면 좋겠다. -> d음..
        final TableGroup tableGroup = tableGroupRepository.findById(tableGroupId).orElseThrow(IllegalArgumentException::new);
        List<OrderTable> orderTables = tableGroup.getOrderTables();

        //final List<OrderTable> orderTables = orderTableRepository.findAllByTableGroupId(tableGroupId);

        final List<Long> orderTableIds = orderTables.stream()
                .map(OrderTable::getId)
                .collect(Collectors.toList());

        if (orderRepository.existsByOrderTableIdInAndOrderStatusIn(
                orderTableIds, Arrays.asList(OrderStatus.COOKING, OrderStatus.MEAL))) {
            throw new IllegalArgumentException();
        }
        tableGroup.upGroupOrderTables();
//        for (final OrderTable orderTable : orderTables) {
//            orderTable.updateTableGroupId(null);
//            orderTableDao.save(orderTable);
//        }
    }
}
