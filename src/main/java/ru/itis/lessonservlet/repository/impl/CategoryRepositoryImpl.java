package ru.itis.lessonservlet.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.lessonservlet.dto.request.CategoryRequest;
import ru.itis.lessonservlet.mapper.CategoryMapper;
import ru.itis.lessonservlet.model.CategoryEntity;
import ru.itis.lessonservlet.repository.CategoryRepository;


import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_INSERT_CATEGORY = "insert into category(name) values (?)";

    private static final String SQL_SELECT_BY_ID = "select * from category where id = ?";

    private static final String SQL_CATEGORY_BY_NAME = "select * from category where name = ?";

    private static final String SQL_INSERT_PRODUCT_CATEGORY = "insert into product_category (product_id, category_id) values (?, ?)";

    private static final String SQL_SELECT_ALL_CATEGORY = "select * from category";

    private static final String SQL_SELECT_CATEGORIES_BY_PRODUCT_ID =
            "select c.id, c.name FROM category c " +
                    "join product_category pc on c.id = pc.category_id " +
                    "where pc.product_id = ?";

    private final CategoryMapper categoryMapper;

    @Override
    public Integer addCategoryAndGetId(String categoryName) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    SQL_INSERT_CATEGORY, new String[]{"id"});
            ps.setString(1, categoryName);
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Override
    public Optional<Integer> findCategoryByName(String categoryName) {
        return jdbcTemplate.query(SQL_CATEGORY_BY_NAME,
                (rs, rowNum) -> rs.getInt("id"), categoryName).stream().findFirst();
    }

    @Override
    public List<CategoryEntity> findCategoriesByProductId(Long productId) {
        return jdbcTemplate.query(SQL_SELECT_CATEGORIES_BY_PRODUCT_ID, categoryMapper, productId);
    }

    @Override
    public void saveProductCategories(Long productId, List<CategoryRequest> categories) {
        categories.forEach(category -> {
            Integer categoryId = findCategoryByName(category.getName())
                    .orElseGet(() -> addCategoryAndGetId(category.getName()));

            jdbcTemplate.update(SQL_INSERT_PRODUCT_CATEGORY,
                    productId, categoryId);
        });
    }

    @Override
    public Optional<CategoryEntity> findCategoryById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, categoryMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<CategoryEntity> getAllCategories() {
        return jdbcTemplate.query(SQL_SELECT_ALL_CATEGORY, categoryMapper);
    }
}
