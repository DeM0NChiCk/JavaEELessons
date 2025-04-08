package ru.itis.lessonservlet.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.itis.lessonservlet.model.OrdersEntity;
import ru.itis.lessonservlet.repository.OrdersRepository;
import ru.itis.lessonservlet.service.OrdersService;


import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrdersService {

    private final OrdersRepository ordersRepository;

    @Override
    public void createOrder(OrdersEntity order) {
        ordersRepository.save(order);
    }

    @Override
    public List<OrdersEntity> getOrdersByUserId(Long userId) {
        return ordersRepository.findAllByUserId(userId);
    }

    @Override
    public void updateOrderStatus(Long orderId, String statusCode) {
        ordersRepository.updateStatus(orderId, statusCode);
    }

    @Override
    public void deleteOrder(Long orderId) {
        ordersRepository.delete(orderId);
    }
}
