package ru.itis.lessonservlet.dto.response;

import lombok.*;
import ru.itis.lessonservlet.model.CategoryEntity;

import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private Long id;

    private String name;

    private String description;

    private double price;

    private int quantity;

    private String image;

    private List<CategoryEntity> category;
}
