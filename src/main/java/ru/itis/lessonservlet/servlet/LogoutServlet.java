package ru.itis.lessonservlet.servlet;


import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    private String AUTHORIZATION;
    private String IS_ADMIN;

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        AUTHORIZATION = (String) context.getAttribute("AUTHORIZATION");
        IS_ADMIN = (String) context.getAttribute("IS_ADMIN");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        session.setAttribute(AUTHORIZATION, false);
        session.setAttribute(IS_ADMIN, false);
        resp.sendRedirect("/");
    }
}
