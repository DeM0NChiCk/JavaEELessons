package ru.itis.semestr.dto.response;

import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrdersResponse {

    private Date orderDate;

    private List<OrderItemResponse> orderItems;

    private UUID orderNumber;

    private String statusCode;

}
