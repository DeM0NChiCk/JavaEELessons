package ru.itis.semestr.dto.response;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {

    private ProductApiResponse product;

    private Integer quantity;

}
