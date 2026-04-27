package ru.itis.semestr.dto.response;

import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GitHubUserResponse {

    private String login;

    private Long id;

    private String name;

    private String email;

}
