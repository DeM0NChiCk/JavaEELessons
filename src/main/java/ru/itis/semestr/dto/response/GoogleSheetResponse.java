package ru.itis.semestr.dto.response;

import lombok.*;

import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GoogleSheetResponse {

    private List<List<String>> values;

}
