package ru.itis.lessonservlet.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.itis.lessonservlet.dto.request.NewOrderItemRequest;
import ru.itis.lessonservlet.dto.request.NewOrdersRequest;
import ru.itis.lessonservlet.entity.OrderItemEntity;
import ru.itis.lessonservlet.entity.OrdersEntity;

@Mapper(componentModel = "spring")
public interface OrderRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "orderNumber", ignore = true)
    OrdersEntity toOrderEntity(NewOrdersRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product", ignore = true)
    OrderItemEntity toOrderItemEntity(NewOrderItemRequest request);

}
