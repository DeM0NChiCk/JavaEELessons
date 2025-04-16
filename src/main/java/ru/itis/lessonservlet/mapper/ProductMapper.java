package ru.itis.lessonservlet.mapper;

import org.mapstruct.Mapper;
import org.springframework.jdbc.core.RowMapper;
import ru.itis.lessonservlet.dto.request.NewProductRequest;
import ru.itis.lessonservlet.dto.response.ListProductsResponse;
import ru.itis.lessonservlet.dto.response.ProductResponse;
import ru.itis.lessonservlet.model.ProductEntity;

import java.util.List;

@Mapper(componentModel = "sping", uses = CategoryMapper.class)
public interface ProductMapper {

    ProductEntity toEntity(NewProductRequest request);

    ProductResponse toDto(ProductEntity entity);

    List<ProductResponse> toDtoList(List<ProductEntity> entities);

    ListProductsResponse toDto(List<ProductEntity> entities);
}
