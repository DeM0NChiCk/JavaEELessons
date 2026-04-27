package ru.itis.semestr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.semestr.entity.OrderItemEntity;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
    List<OrderItemEntity> findAllByOrderId(Long orderId);
}
