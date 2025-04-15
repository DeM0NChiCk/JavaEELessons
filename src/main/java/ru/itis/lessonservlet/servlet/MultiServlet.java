package ru.itis.lessonservlet.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/calculator/multi")
public class MultiServlet extends BaseOperationServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String value1 = req.getParameter("value1");
        String value2 = req.getParameter("value2");

        if (!validateParameters(value1, value2, resp)) return;

        try {
            int a = Integer.parseInt(value1);
            int b = Integer.parseInt(value2);
            int result = calculator.multiply(a, b);
            showResult(resp, "умножения", result);
        } catch (NumberFormatException e) {
            showError(resp, "Некорректный ввод. Пожалуйста, введите целые числа.");
        }
    }
}