package ru.itis.semestr.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.semestr.dto.request.NewOrdersRequest;
import ru.itis.semestr.dto.response.OrdersAllResponse;
import ru.itis.semestr.dto.response.OrdersResponse;
import ru.itis.semestr.entity.OrderItemEntity;
import ru.itis.semestr.entity.OrdersEntity;
import ru.itis.semestr.entity.ProductEntity;
import ru.itis.semestr.entity.UserEntity;
import ru.itis.semestr.mapper.OrderRequestMapper;
import ru.itis.semestr.mapper.OrderResponseMapper;
import ru.itis.semestr.repository.OrdersRepository;
import ru.itis.semestr.repository.ProductRepository;
import ru.itis.semestr.repository.UserRepository;
import ru.itis.semestr.service.OrdersService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrdersService {

    private final OrdersRepository ordersRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    private final OrderRequestMapper orderRequestMapper;
    private final OrderResponseMapper orderResponseMapper;

    @Override
    public OrdersResponse createOrderIfNotExists(NewOrdersRequest request, Long userId) {
        UUID generatedOrderNumber = UUID.randomUUID();

        while (ordersRepository.existsByOrderNumber(generatedOrderNumber)) {
            generatedOrderNumber = UUID.randomUUID();
        }

        OrdersEntity order = orderRequestMapper.toOrderEntity(request);
        order.setOrderNumber(generatedOrderNumber);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatusCode(OrdersEntity.STATUS_PENDING);

        List<OrderItemEntity> orderItems = request.getItems().stream()
                .map(itemRequest -> {
                    OrderItemEntity item = orderRequestMapper.toOrderItemEntity(itemRequest);
                    ProductEntity product = productRepository.findById(itemRequest.getProductId())
                            .orElseThrow(() -> new IllegalArgumentException("Товар не найден: " + itemRequest.getProductId()));
                    item.setProduct(product);
                    item.setOrder(order);
                    return item;
                })
                .collect(Collectors.toList());

        order.setItems(orderItems);
        OrdersEntity savedOrder = ordersRepository.save(order);

        return orderResponseMapper.toResponse(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdersResponse> getOrdersByUserId(Long userId) {
        List<OrdersEntity> orders = ordersRepository.findAllByUserId(userId);
        return orderResponseMapper.toResponseList(orders);
    }

    @Override
    public List<OrdersAllResponse> getAllOrders() {
        return orderResponseMapper.toResponseAllList(ordersRepository.findAll());
    }

    @Override
    public void updateOrderStatus(UUID orderNumber, String statusCode) {
        if (!ordersRepository.existsByOrderNumber(orderNumber)) {
            throw new IllegalArgumentException("Заказ с данным номером не найден:" + orderNumber);
        }

        ordersRepository.updateStatus(orderNumber, statusCode);
    }

    @Override
    public Optional<OrdersResponse> findByOrderNumber(UUID orderNumber) {
        return ordersRepository.findByOrderNumber(orderNumber)
                .map(orderResponseMapper::toResponse);
    }
}
