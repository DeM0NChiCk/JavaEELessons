package ru.itis.lessonservlet.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "introServlet", value = "/")
public class IntroServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();

        out.println("<html><body>");
        out.println("<h1>Добро пожаловать в калькулятор</h1>");
        out.println("<h2>Меня зовут: Калькулятор</h2>");
        out.println("<form action='/calculator' method='get'>");
        out.println("<button type='submit'>Начать работу</button>");
        out.println("</form>");
        out.println("</body></html>");
    }
}