package ru.itis.lessonservlet.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.itis.lessonservlet.dto.request.NewProductRequest;
import ru.itis.lessonservlet.dto.response.ListProductsResponse;
import ru.itis.lessonservlet.model.ProductEntity;

import java.util.List;

public interface ProductMapper extends RowMapper<ProductEntity> {

    ProductEntity toEntity(NewProductRequest request);

    ListProductsResponse toDto(List<ProductEntity> entity);
}
