package ru.itis.lessonservlet.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private Long userId;

    private Long productId;

    private LocalDateTime orderDate;

    private String statusCode;
}
