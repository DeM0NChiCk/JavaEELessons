package ru.itis.semestr.service;

import ru.itis.semestr.dto.request.NewOrdersRequest;
import ru.itis.semestr.dto.response.OrdersAllResponse;
import ru.itis.semestr.dto.response.OrdersResponse;

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
