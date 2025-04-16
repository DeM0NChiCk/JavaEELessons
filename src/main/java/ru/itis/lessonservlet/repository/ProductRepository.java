package ru.itis.lessonservlet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.itis.lessonservlet.dto.request.CategoryRequest;
import ru.itis.lessonservlet.model.ProductEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT DISTINCT p FROM ProductEntity p LEFT JOIN FETCH p.categories")
    List<ProductEntity> findAllWithCategories();

}
