package ru.itis.lessonservlet.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.itis.lessonservlet.dto.request.OrderRequest;
import ru.itis.lessonservlet.entity.OrdersEntity;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderDate", ignore = true)
    @Mapping(target = "statusCode", ignore = true)
    OrdersEntity toEntity(OrderRequest request);


}
