package ru.itis.lessonservlet.service;

import ru.itis.lessonservlet.dto.request.OrderRequest;
import ru.itis.lessonservlet.entity.OrdersEntity;

import java.util.List;

public interface OrdersService {

    void createOrder(OrderRequest order);

    List<OrdersEntity> getOrdersByUserId(Long userId);

    void updateOrderStatus(Long orderId, String statusCode);

    void deleteOrder(Long orderId);
}
