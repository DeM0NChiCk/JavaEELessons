package ru.itis.semestr.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.itis.semestr.dto.request.NewProductRequest;
import ru.itis.semestr.dto.response.ListProductsResponse;
import ru.itis.semestr.dto.response.ProductApiResponse;
import ru.itis.semestr.dto.response.ProductResponse;
import ru.itis.semestr.entity.ProductEntity;
import ru.itis.semestr.utils.ImageUtils;

import java.util.List;

@Mapper(componentModel = "spring", uses = CategoryMapper.class)
public interface ProductMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "isFavorite", ignore = true)
    ProductEntity toEntity(NewProductRequest request);

    @Mapping(source = "image", target = "image", qualifiedByName = "imageToString")
    ProductResponse toDto(ProductEntity entity);

    List<ProductResponse> toDtoList(List<ProductEntity> entities);

    @Mapping(source = "image", target = "image", qualifiedByName = "imageToString")
    ProductApiResponse toDtoApi(ProductEntity entity);

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
