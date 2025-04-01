package ru.itis.lessonservlet.servlet;


import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.lessonservlet.dto.request.SignInRequest;
import ru.itis.lessonservlet.dto.response.AuthResponse;
import ru.itis.lessonservlet.service.UserService;

import java.io.IOException;

@WebServlet("/signIn")
public class SignInServlet extends HttpServlet {

    private UserService userService;

    private String AUTHORIZATION;
    private String IS_ADMIN;

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        userService = (UserService) context.getAttribute("userService");
        AUTHORIZATION = (String) context.getAttribute("AUTHORIZATION");
        IS_ADMIN = (String) context.getAttribute("IS_ADMIN");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("jsp/signIn.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SignInRequest signInRequest = SignInRequest.builder()
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .build();

        AuthResponse authResponse = userService.signIn(signInRequest);
        if(authResponse.getStatus() == 0) {
            HttpSession session = req.getSession(true);
            session.setAttribute(AUTHORIZATION, true);
            session.setAttribute(IS_ADMIN, false);
            session.setAttribute("user", authResponse.getUser());
            resp.sendRedirect("/main");
        } else {
            resp.sendRedirect("/error?err=" + authResponse.getStatusDesc());
        }

    }

}
