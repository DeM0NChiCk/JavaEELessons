package ru.itis.lessonservlet.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.lessonservlet.entity.CategoryEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    @Query("SELECT c FROM CategoryEntity c WHERE c.name = :paranName")
    Optional<CategoryEntity> findByName(@Param("paramName") String name);

    @Query("SELECT c.id FROM CategoryEntity c WHERE c.name = :paramName")
    Optional<Integer> findCategoryIdByName(@Param("paramName") String name);

    @Query("SELECT c FROM ProductEntity p JOIN p.categories c WHERE p.id = :productId")
    List<CategoryEntity> findCategoriesByProductId(@Param("productId") Long productId);
}
