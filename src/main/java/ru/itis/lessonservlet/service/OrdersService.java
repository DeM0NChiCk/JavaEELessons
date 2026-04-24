package ru.itis.lessonservlet.service;

import ru.itis.lessonservlet.dto.request.NewOrderRequest;
import ru.itis.lessonservlet.dto.request.NewOrdersRequest;
import ru.itis.lessonservlet.dto.response.OrdersAllResponse;
import ru.itis.lessonservlet.dto.response.OrdersResponse;
import ru.itis.lessonservlet.entity.OrdersEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrdersService {

    OrdersResponse createOrderIfNotExists(NewOrdersRequest request, Long userId);

    List<OrdersResponse> getOrdersByUserId(Long userId);

    List<OrdersAllResponse> getAllOrders();

    void updateOrderStatus(UUID orderNumber, String statusCode);

    Optional<OrdersResponse> findByOrderNumber(UUID orderNumber);
}
