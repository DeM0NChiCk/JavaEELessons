package ru.itis.lessonservlet.repository;



import ru.itis.lessonservlet.model.OrdersEntity;

import java.util.List;

public interface OrdersRepository {
    void save(OrdersEntity order);
    List<OrdersEntity> findAllByUserId(Long userId);
    void updateStatus(Long orderId, String statusCode);
    void delete(Long orderId);
}
