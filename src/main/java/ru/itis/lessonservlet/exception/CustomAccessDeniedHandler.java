package ru.itis.lessonservlet.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String errorMessage = URLEncoder.encode("Ошибка 403: У вас нет прав доступа." + accessDeniedException.getMessage(), StandardCharsets.UTF_8);

        log.warn(errorMessage, accessDeniedException.getMessage());
        response.sendRedirect("/error-auth?err=" + errorMessage);
    }
}
