package ru.itis.lessonservlet.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.lessonservlet.service.Calculator;
import ru.itis.lessonservlet.service.impl.CalculatorImpl;

import java.io.IOException;
import java.io.PrintWriter;

public abstract class BaseOperationServlet extends HttpServlet {
    protected Calculator calculator;

    @Override
    public void init() {
        calculator = new CalculatorImpl();
    }

    protected void showResult(HttpServletResponse resp, String operationName, int result) throws IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("<h2>Результат " + operationName + ": " + result + "</h2>");
        out.println("<a href='/calculator'>Вернуться к калькулятору</a>");
        out.println("</body></html>");
    }

    protected void showError(HttpServletResponse resp, String message) throws IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("<h2 style='color:red'>Ошибка: " + message + "</h2>");
        out.println("<a href='/calculator'>Вернуться к калькулятору</a>");
        out.println("</body></html>");
    }

    protected boolean validateParameters(String value1, String value2, HttpServletResponse resp) throws IOException {
        if (value1 == null || value2 == null || value1.isEmpty() || value2.isEmpty()) {
            showError(resp, "Необходимо заполнить оба поля");
            return false;
        }
        return true;
    }
}