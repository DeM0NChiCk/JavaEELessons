package ru.itis.lessonservlet.service;

import ru.itis.lessonservlet.dto.request.NewOrderRequest;
import ru.itis.lessonservlet.entity.OrdersEntity;

import java.util.List;

public interface OrdersService {

    void createOrder(NewOrderRequest order);

    List<OrdersEntity> getOrdersByUserId(Long userId);

    void updateOrderStatus(Long orderId, String statusCode);

    void deleteOrder(Long orderId);
}
