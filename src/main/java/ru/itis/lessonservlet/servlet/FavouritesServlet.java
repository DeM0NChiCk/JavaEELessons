package ru.itis.lessonservlet.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.lessonservlet.dto.response.ListProductsResponse;
import ru.itis.lessonservlet.dto.response.UserDataResponse;
import ru.itis.lessonservlet.service.FavouritesService;

import java.io.IOException;

@WebServlet("/favourites")
public class FavouritesServlet extends HttpServlet {

    private FavouritesService favouritesService;

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        favouritesService = (FavouritesService) context.getAttribute("favouritesService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);

        UserDataResponse user = (UserDataResponse) session.getAttribute("user");

        ListProductsResponse listProductsResponse = favouritesService.getAllFavorites(user.getId());

        session.setAttribute("favourites", listProductsResponse);

        req.getRequestDispatcher("jsp/favourites.jsp").forward(req, resp);
    }
}