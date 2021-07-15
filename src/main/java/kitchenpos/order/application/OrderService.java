package kitchenpos.order.application;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderLineItem;
import kitchenpos.order.domain.OrderStatus;
import kitchenpos.table.domain.OrderTable;
import kitchenpos.order.domain.repository.OrderLineItemRepository;
import kitchenpos.order.domain.repository.OrderRepository;
import kitchenpos.table.domain.OrderTableRepository;
import kitchenpos.order.dto.OrderRequest;
import kitchenpos.order.dto.OrderResponse;
import kitchenpos.order.event.OrderCreatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderTableRepository orderTableRepository;
    private final OrderLineItemRepository orderLineItemRepository;
    private final ApplicationEventPublisher publisher;

    public OrderService(
            final OrderRepository orderRepository,
            final OrderTableRepository orderTableRepository,
            final OrderLineItemRepository orderLineItemRepository,
            final ApplicationEventPublisher publisher
    ) {
        this.orderRepository = orderRepository;
        this.orderTableRepository = orderTableRepository;
        this.orderLineItemRepository = orderLineItemRepository;
        this.publisher = publisher;
    }

    @Transactional
    public OrderResponse create(final OrderRequest orderRequest) {
        final OrderTable orderTable = findOrderTable(orderRequest);
        final Order persistOrder = orderRepository.save(new Order(orderTable, OrderStatus.COOKING));
        publisher.publishEvent(new OrderCreatedEvent(persistOrder, orderRequest.toOrderLineItems()));
        return OrderResponse.of(persistOrder, findOrderLineItems(orderTable.getId()));
    }

    public List<OrderResponse> list() {
        final List<Order> orders = orderRepository.findAll();

        return orders.stream()
                .map(order -> new OrderResponse(order, findOrderLineItems(order.getId())))
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponse changeOrderStatus(final Long orderId, final OrderRequest order) {
        final Order savedOrder = findOrderById(orderId);
        savedOrder.updateOrderStatus(order.getOrderStatus());
        return OrderResponse.of(savedOrder, findOrderLineItems(savedOrder.getId()));
    }

    private OrderTable findOrderTable(OrderRequest orderRequest) {
        return orderTableRepository.findById(orderRequest.getOrderTableId())
                .orElseThrow(() -> new IllegalArgumentException("등록이 안된 주문 테이블에서는 주문할 수 없습니다."));
    }

    public Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(IllegalArgumentException::new);
    }

    public List<OrderLineItem> findOrderLineItems(Long orderId) {
        return orderLineItemRepository.findAllByOrderId(orderId);
    }
}
