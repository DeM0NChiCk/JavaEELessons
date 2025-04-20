package ru.itis.lessonservlet.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.itis.lessonservlet.dto.request.NewProductRequest;
import ru.itis.lessonservlet.dto.response.ListProductsResponse;
import ru.itis.lessonservlet.dto.response.ProductResponse;
import ru.itis.lessonservlet.entity.ProductEntity;
import ru.itis.lessonservlet.utils.ImageUtils;

import java.util.List;

@Mapper(componentModel = "spring", uses = CategoryMapper.class)
public interface ProductMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "isFavorite", ignore = true)
    ProductEntity toEntity(NewProductRequest request);

    @Mapping(source = "image", target = "image", qualifiedByName = "imageToString")
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "isFavorite", ignore = true)
    ProductResponse toDto(ProductEntity entity);

    List<ProductResponse> toDtoList(List<ProductEntity> entities);

    default ListProductsResponse toDto(List<ProductEntity> entities) {
        return ListProductsResponse.builder()
                .products(toDtoList(entities))
                .build();
    }

    @Named("imageToString")
    static String imageToString(byte[] image) {
        return ImageUtils.encodeToBase64(image);
    }

}
