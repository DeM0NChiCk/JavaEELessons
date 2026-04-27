package ru.itis.semestr.service;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AdminOAuthService {

    String handleGithubOAuthCallback(String code);

    void redirectToGithub(HttpServletResponse response) throws IOException;

}
