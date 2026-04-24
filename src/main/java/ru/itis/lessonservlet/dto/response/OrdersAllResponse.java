package ru.itis.lessonservlet.dto.response;

import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrdersAllResponse {

    private Date orderDate;

    private List<OrderItemResponse> orderItems;

    private UserDataResponse user;

    private UUID orderNumber;

    private String statusCode;

}
