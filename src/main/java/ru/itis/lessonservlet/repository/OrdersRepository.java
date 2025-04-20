package ru.itis.lessonservlet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.lessonservlet.entity.OrdersEntity;

import java.util.List;

public interface OrdersRepository extends JpaRepository<OrdersEntity, Long> {
    List<OrdersEntity> findAllByUserId(Long userId);

    @Modifying
    @Query("UPDATE OrdersEntity o SET o.statusCode = :statusCode WHERE o.id = :orderId")
    void updateStatus(@Param("orderId") Long orderId, @Param("statusCode") String statusCode);
}
