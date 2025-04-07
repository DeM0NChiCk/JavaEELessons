package ru.itis.lessonservlet.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "calculatorFormServlet", value = "/calculator")
public class CalculatorFormServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();

        out.println("<html><body>");
        out.println("<h1>Калькулятор</h1>");
        out.println("<p>Введите два целых числа и выберите операцию</p>");
        out.println("<form method='post'>");
        out.println("Число 1: <input type='text' name='value1' required><br>");
        out.println("Число 2: <input type='text' name='value2' required><br>");
        out.println("<button type='submit' formaction='calculator/sum'>Сложить</button>");
        out.println("<button type='submit' formaction='calculator/sub'>Вычесть</button>");
        out.println("<button type='submit' formaction='calculator/multi'>Умножить</button>");
        out.println("<button type='submit' formaction='calculator/div'>Разделить</button>");
        out.println("</form>");
        out.println("</body></html>");
    }
}