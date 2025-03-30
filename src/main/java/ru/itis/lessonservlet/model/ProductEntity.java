package ru.itis.lessonservlet.model;

import lombok.*;

import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {
    Long id;

    String name;

    String description;

    double price;

    int quantity;

    byte[] image;

    private List<CategoryEntity> categories;
}
