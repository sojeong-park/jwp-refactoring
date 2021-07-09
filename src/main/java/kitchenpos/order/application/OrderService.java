package kitchenpos.order.application;

import kitchenpos.common.Quantity;
import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuRepository;
import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderLineItem;
import kitchenpos.order.domain.OrderStatus;
import kitchenpos.order.domain.OrderTable;
import kitchenpos.order.domain.repository.OrderRepository;
import kitchenpos.order.domain.repository.OrderTableRepository;
import kitchenpos.order.dto.OrderLineItemRequest;
import kitchenpos.order.dto.OrderRequest;
import kitchenpos.order.dto.OrderResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;
    private final OrderTableRepository orderTableRepository;

    public OrderService(
            final MenuRepository menuRepository,
            final OrderRepository orderRepository,
            final OrderTableRepository orderTableRepository
    ) {
        this.menuRepository = menuRepository;
        this.orderRepository = orderRepository;
        this.orderTableRepository = orderTableRepository;
    }

    @Transactional
    public OrderResponse create(final OrderRequest orderRequest) {
        final List<OrderLineItemRequest> orderLineItemsRequest = orderRequest.getOrderLineItems();

        List<Menu> menus = findMenuAllById(orderLineItemsRequest);
        validOrderLineItemEmpty(orderLineItemsRequest);
        validOrderLIneItemCount(orderLineItemsRequest, menus.size());

        final OrderTable orderTable = findOrderTableById(orderRequest.getOrderTableId());
        validOrderTableEmpty(orderTable);

        final Order savedOrder =new Order(orderTable, OrderStatus.COOKING);

        for (OrderLineItemRequest orderLineItemRequest : orderLineItemsRequest) {
            Menu findMenu = menus.stream()
                    .filter(menu -> menu.getId().equals(orderLineItemRequest.getMenuId()))
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);
            OrderLineItem.of(savedOrder, findMenu, new Quantity(orderLineItemRequest.getQuantity()));
        }

        return OrderResponse.of(orderRepository.save(savedOrder));
    }

    public List<OrderResponse> list() {
        final List<Order> orders = orderRepository.findAll();

        return orders.stream()
                .map(OrderResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponse changeOrderStatus(final Long orderId, final Order order) {
        final Order savedOrder = findOrderById(orderId);
        savedOrder.updateOrderStatus(order.getOrderStatus());
        return OrderResponse.of(savedOrder);
    }

    private void validOrderTableEmpty(OrderTable orderTable) {
        if (orderTable.getEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    private OrderTable findOrderTableById(Long orderTableId) {
        return orderTableRepository.findById(orderTableId).orElseThrow(IllegalArgumentException::new);
    }

    private void validOrderLineItemEmpty(List<OrderLineItemRequest> orderLineItemsRequest) {
        if (CollectionUtils.isEmpty(orderLineItemsRequest)) {
            throw new IllegalArgumentException();
        }
    }
    private void validOrderLIneItemCount(List<OrderLineItemRequest> orderLineItemsRequest, int menuSize) {
        if (orderLineItemsRequest.size() != menuSize) {
            throw new IllegalArgumentException();
        }
    }

    private List<Menu> findMenuAllById(List<OrderLineItemRequest> orderLineItemsRequest) {
        final List<Long> menuIds = orderLineItemsRequest.stream()
                .map(OrderLineItemRequest::getMenuId)
                .collect(Collectors.toList());
        return menuRepository.findAllById(menuIds);
    }

    private Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(IllegalArgumentException::new);
    }
}
