package ru.itis.lessonservlet.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.lessonservlet.dto.request.NewOrderRequest;
import ru.itis.lessonservlet.entity.OrdersEntity;
import ru.itis.lessonservlet.entity.ProductEntity;
import ru.itis.lessonservlet.entity.UserEntity;
import ru.itis.lessonservlet.repository.OrdersRepository;
import ru.itis.lessonservlet.repository.ProductRepository;
import ru.itis.lessonservlet.repository.UserRepository;
import ru.itis.lessonservlet.service.OrdersService;


import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrdersService {


    private final OrdersRepository ordersRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void createOrder(NewOrderRequest request) {

        // Загрузка пользователя и продукта по ID
        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + request.getUserId()));

        ProductEntity product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + request.getProductId()));

        OrdersEntity order = OrdersEntity.builder()
                .user(user)
                .product(product)
                .orderDate(LocalDateTime.now())
                .statusCode(OrdersEntity.STATUS_PENDING)
                .build();

        ordersRepository.save(order);

        log.info("Order created: userId={}, productId={}", request.getUserId(), request.getProductId());
    }

    @Override
    public List<OrdersEntity> getOrdersByUserId(Long userId) {
        return ordersRepository.findAllByUserId(userId);
    }

    @Override
    @Transactional
    public void updateOrderStatus(Long orderId, String statusCode) {
        ordersRepository.updateStatus(orderId, statusCode);
        log.info("Updated order status: orderId={}, status={}", orderId, statusCode);
    }

    @Override
    @Transactional
    public void deleteOrder(Long orderId) {
        ordersRepository.deleteById(orderId);
        log.info("Deleted order with id={}", orderId);
    }
}
