package ru.itis.lessonservlet.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.lessonservlet.dto.request.OrderRequest;
import ru.itis.lessonservlet.mapper.OrderMapper;
import ru.itis.lessonservlet.model.OrdersEntity;
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
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void createOrder(OrderRequest request) {
        OrdersEntity order = orderMapper.toEntity(request);

        order.setOrderDate(LocalDateTime.now()); // или request.getOrderDate(), если хочешь вручную
        order.setStatusCode(OrdersEntity.STATUS_PENDING); // по умолчанию

        // Загрузка пользователя и продукта по ID
        order.setUser(userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + request.getUserId())));

        order.setProduct(productRepository.findById(request.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + request.getProductId())));

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
