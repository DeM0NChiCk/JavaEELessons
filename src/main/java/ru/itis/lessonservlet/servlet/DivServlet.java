package ru.itis.lessonservlet.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/calculator/div")
public class DivServlet extends BaseOperationServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String value1 = req.getParameter("value1");
        String value2 = req.getParameter("value2");

        if (!validateParameters(value1, value2, resp)) return;

        try {
            int a = Integer.parseInt(value1);
            int b = Integer.parseInt(value2);

            if (b == 0) {
                showError(resp, "Деление на ноль невозможно");
                return;
            }

            int result = calculator.divide(a, b);
            showResult(resp, "деления", result);
        } catch (NumberFormatException e) {
            showError(resp, "Некорректный ввод. Пожалуйста, введите целые числа.");
        }
    }
}