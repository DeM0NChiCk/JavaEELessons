package ru.itis.lessonservlet.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.itis.lessonservlet.model.OrdersEntity;

import java.util.List;

public interface OrdersRepository extends JpaRepository<OrdersEntity, Long> {
    List<OrdersEntity> findAllByUserId(Long userId);

    @Modifying
    @Query("UPDATE OrdersEntity o SET o.statusCode = ?2 WHERE o.id = ?1")
    void updateStatus(Long orderId, String statusCode);
}
