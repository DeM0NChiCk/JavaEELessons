package ru.itis.lessonservlet.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.itis.lessonservlet.model.CategoryEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    @Query("SELECT c FROM CategoryEntity c WHERE c.name = ?1")
    Optional<CategoryEntity> findByName(String name);

    @Query("SELECT c.id FROM CategoryEntity c WHERE c.name = ?1")
    Optional<Integer> findCategoryIdByName(String name);

    @Query("SELECT c FROM ProductEntity p JOIN p.categories c WHERE p.id = ?")
    List<CategoryEntity> findCategoriesByProductId(Long productId);

    @Query("SELECT c FROM CategoryEntity c")
    List<CategoryEntity> getAllCategories();
}
