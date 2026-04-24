package ru.itis.lessonservlet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.lessonservlet.entity.OrdersEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrdersRepository extends JpaRepository<OrdersEntity, Long> {

    Optional<OrdersEntity> findByOrderNumber(UUID orderNumber);

    List<OrdersEntity> findAllByUserId(Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE OrdersEntity o SET o.statusCode = :status WHERE o.orderNumber = :orderNumber")
    void updateStatus(@Param("orderNumber") UUID orderNumber, @Param("status") String status);

    boolean existsByOrderNumber(UUID orderNumber);
}
