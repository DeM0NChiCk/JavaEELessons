package ru.itis.lessonservlet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class OrdersEntity {
    public final static String STATUS_PENDING = "pending";
    public final static String STATUS_APPROVED = "completed";
    public final static String STATUS_REJECTED = "cancelled";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Pattern(regexp = "pending|completed|cancelled", message = "Invalid status")
    @Column(name = "status_code", nullable = false)
    private String statusCode;
}
