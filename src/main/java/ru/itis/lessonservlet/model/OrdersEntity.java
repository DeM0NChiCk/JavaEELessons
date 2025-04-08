package ru.itis.lessonservlet.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrdersEntity {
    public final static String STATUS_PENDING = "pending";
    public final static String STATUS_APPROVED = "completed";
    public final static String STATUS_REJECTED = "cancelled";

    private Long id;

    private Long userId;

    private Long productId;

    private LocalDateTime orderDate;

    private String statusCode;
}
