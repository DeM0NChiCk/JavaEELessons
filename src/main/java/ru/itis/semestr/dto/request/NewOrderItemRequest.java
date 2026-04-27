package ru.itis.semestr.dto.request;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NewOrderItemRequest {
    private Long productId;
    private Integer quantity;
}
