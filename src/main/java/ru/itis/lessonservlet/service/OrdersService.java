package ru.itis.lessonservlet.service;



import ru.itis.lessonservlet.model.OrdersEntity;

import java.util.List;

public interface OrdersService {

    void createOrder(OrdersEntity order);

    List<OrdersEntity> getOrdersByUserId(Long userId);

    void updateOrderStatus(Long orderId, String statusCode);

    void deleteOrder(Long orderId);
}
