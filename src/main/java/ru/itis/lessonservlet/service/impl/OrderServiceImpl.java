package ru.itis.lessonservlet.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itis.lessonservlet.dto.request.NewOrdersRequest;
import ru.itis.lessonservlet.dto.response.OrdersAllResponse;
import ru.itis.lessonservlet.dto.response.OrdersResponse;
import ru.itis.lessonservlet.entity.OrderItemEntity;
import ru.itis.lessonservlet.entity.OrdersEntity;
import ru.itis.lessonservlet.entity.ProductEntity;
import ru.itis.lessonservlet.entity.UserEntity;
import ru.itis.lessonservlet.mapper.OrderRequestMapper;
import ru.itis.lessonservlet.mapper.OrderResponseMapper;
import ru.itis.lessonservlet.repository.OrdersRepository;
import ru.itis.lessonservlet.repository.ProductRepository;
import ru.itis.lessonservlet.repository.UserRepository;
import ru.itis.lessonservlet.service.OrdersService;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
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
