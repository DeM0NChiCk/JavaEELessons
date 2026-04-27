package ru.itis.semestr.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
@AllArgsConstructor
public class AuthApiResponse {

    private int status;

    private String statusDesc;

    private String token;

}
