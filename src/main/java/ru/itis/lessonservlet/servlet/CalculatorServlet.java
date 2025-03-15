package ru.itis.lessonservlet.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.lessonservlet.service.Calculator;
import ru.itis.lessonservlet.service.impl.CalculatorImpl;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "calculator", value = "/calculator")
public class CalculatorServlet extends HttpServlet {
    private Calculator calculator;

    @Override
    public void init() {
        calculator = new CalculatorImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int a = Integer.parseInt(req.getParameter("value1"));
        int b = Integer.parseInt(req.getParameter("value2"));

        int result = calculator.sum(a, b);

        PrintWriter out = resp.getWriter();

        out.println("<html><body>");
        out.println("<h2> Result: " + result + "</h2>");
        out.println("</body></html>");
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
