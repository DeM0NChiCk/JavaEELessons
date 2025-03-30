package ru.itis.lessonservlet.dto.response;

import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private Long id;
    private String name;
}
