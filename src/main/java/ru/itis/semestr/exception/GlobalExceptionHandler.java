package ru.itis.semestr.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.itis.semestr.controller.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice(assignableTypes = {
        AdminAuthController.class,
        AdminInfoController.class,
        AdminNewsControllers.class,
        AdminOrderController.class,
        AdminProductController.class,
        AuthController.class,
        FavoriteController.class,
        ErrorController.class,
        GitHubOAuthController.class,
        InfoController.class,
        OrderController.class,
        ProductController.class,
        ToggleController.class
})
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationError(MethodArgumentNotValidException ex) {
        String errorMessages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::formatFieldError)
                .collect(Collectors.joining("; "));

        log.warn("Ошибка валидации: {}", errorMessages);

        String errorMessage = URLEncoder.encode("Ошибка валидации: " + errorMessages, StandardCharsets.UTF_8);

        return "redirect:/error-auth?err=" + errorMessage;
    }

    private String formatFieldError(FieldError fieldError) {
        return fieldError.getField() + ": " + fieldError.getDefaultMessage();
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNotFound(NoHandlerFoundException ex) {
        log.warn("Страница не найдена: {}", ex.getRequestURL());
        String errorMessage = URLEncoder.encode("Ошибка 404: Страница не найдена." + ex.getMessage(), StandardCharsets.UTF_8);
        return "redirect:/error-auth?=error=" + errorMessage;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleBadRequest(IllegalArgumentException ex) {
        log.warn("Неверный запрос: {}", ex.getMessage());
        String errorMessage = URLEncoder.encode("Ошибка 400: Неверный запрос. " + ex.getMessage(), StandardCharsets.UTF_8);
        return "redirect:/error-auth?err=" + errorMessage;
    }

    @ExceptionHandler(SecurityException.class)
    public String handleForbidden(SecurityException ex) {
        log.warn("Доступ запрещён: {}", ex.getMessage());
        String errorMessage = URLEncoder.encode("Ошибка 403: Доступ запрещён.", StandardCharsets.UTF_8);
        return "redirect:/error-auth?err=" + errorMessage;
    }

    @ExceptionHandler(NullPointerException.class)
    public String handleNullPointer(NullPointerException ex) {
        log.error("Ошибка null pointer", ex);
        String errorMessage = URLEncoder.encode("Ошибка 500: Внутренняя ошибка сервера.", StandardCharsets.UTF_8);
        return "redirect:/error-auth?err=" + errorMessage;
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public String handleMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        log.warn("Метод запроса не поддерживается: {}", ex.getMethod());
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            String errorMessage = URLEncoder.encode("Метод " + ex.getMethod() + " не поддерживается", StandardCharsets.UTF_8);
            return "redirect:/error-auth?err=" + errorMessage;
        } else {
            String errorMessage = URLEncoder.encode("Ошибка 405: Метод " + ex.getMethod() + " не поддерживается.", StandardCharsets.UTF_8);
            return "error-auth?err=" + errorMessage; // возвращает страницу напрямую
        }
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleMissingParams(MissingServletRequestParameterException ex) {
        log.warn("Отсутствует параметр запроса: {}", ex.getParameterName());
        String errorMessage = URLEncoder.encode("Ошибка 400: Отсутствует параметр - " + ex.getParameterName(), StandardCharsets.UTF_8);
        return "redirect:/error-auth?err=" + errorMessage;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public String handleUnreadableMessage(HttpMessageNotReadableException ex) {
        log.warn("Нераспознанное тело запроса: {}", ex.getMessage());
        String errorMessage = URLEncoder.encode("Ошибка 400: Неверное тело запроса.", StandardCharsets.UTF_8);
        return "redirect:/error-auth?err=" + errorMessage;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDenied(AccessDeniedException ex) {
        log.warn("Доступ запрещён (Spring Security): {}", ex.getMessage());
        String errorMessage = URLEncoder.encode("Ошибка 403: У вас нет прав доступа.", StandardCharsets.UTF_8);
        return "redirect:/error-auth?err=" + errorMessage;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        if (ex.getMessage().contains("positive_price")) {
            String errorMessage = URLEncoder.encode("Ошибка: цена должна быть больше или равна нулю.", StandardCharsets.UTF_8);
            return "redirect:/error-auth?err=" + errorMessage;
        }
        if (ex.getMessage().contains("positive_quantity")) {
            String errorMessage = URLEncoder.encode("Ошибка: количество должно быть больше или равно нулю.", StandardCharsets.UTF_8);

            return "redirect:/error-auth?err=" + errorMessage;
        }

        String errorMessage = URLEncoder.encode("Ошибка целостности данных: " + ex.getMessage(), StandardCharsets.UTF_8);

        return "redirect:/error-auth?err=" + errorMessage;
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralError(Exception ex, HttpServletRequest request) {
        String message = String.format("Необработанная ошибка на [%s]: %s", request.getRequestURI(), ex.getMessage());
        log.error(message, ex);
        String errMessage = String.format("Произошла ошибка на [%s]: %s", request.getRequestURI(), "Сообщите администратору");
        String errorMessage = URLEncoder.encode(errMessage, StandardCharsets.UTF_8);
        return "redirect:/error-auth?err=" + errorMessage;
    }
}
