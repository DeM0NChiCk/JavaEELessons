package ru.itis.semestr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.semestr.entity.ProductEntity;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT DISTINCT p FROM ProductEntity p LEFT JOIN FETCH p.categories")
    List<ProductEntity> findAllWithCategories();

    @Query("SELECT p FROM ProductEntity p WHERE p.id IN ( SELECT oi.product.id FROM OrderItemEntity oi WHERE oi.order.user.id = :userId)")
    List<ProductEntity> findOrderedProductsByUserId(@Param("userId") Long userId);

}
