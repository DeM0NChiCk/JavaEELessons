package ru.itis.semestr.dto.response;

import lombok.*;

import java.util.List;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ListCategoriesResponse {

    private List<CategoryResponse> categories;

}
