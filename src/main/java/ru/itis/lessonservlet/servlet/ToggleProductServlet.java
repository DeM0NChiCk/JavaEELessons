package ru.itis.lessonservlet.servlet;


import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.lessonservlet.dto.response.UserDataResponse;
import ru.itis.lessonservlet.service.FavouritesService;

import java.io.IOException;

@WebServlet("/toggleProduct")
public class ToggleProductServlet extends HttpServlet {

    private FavouritesService favouritesService;

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        favouritesService = (FavouritesService) context.getAttribute("favouritesService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long productId = Long.parseLong(req.getParameter("productId"));
        boolean isFavorite = Boolean.parseBoolean(req.getParameter("isFavorite"));

        HttpSession session = req.getSession(true);
        UserDataResponse user = (UserDataResponse) session.getAttribute("user");

        Long userId = user.getId();

        if (!isFavorite) {
            favouritesService.addFavorite(userId, productId);
        } else {
            favouritesService.deleteFavorite(userId, productId);
        }

        resp.sendRedirect("/products");
    }
}
