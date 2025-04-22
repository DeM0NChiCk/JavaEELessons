package ru.itis.lessonservlet.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.lessonservlet.dto.response.ListProductsResponse;
import ru.itis.lessonservlet.dto.response.UserDataResponse;
import ru.itis.lessonservlet.service.FavouritesService;

@Controller
@RequestMapping("/favourites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavouritesService favouritesService;

    @GetMapping
    public String getFavouritesPage(HttpSession session) {
        UserDataResponse user = (UserDataResponse) session.getAttribute("user");

        ListProductsResponse listProductsResponse = favouritesService.getAllFavorites(user.getId());

        session.setAttribute("favourites", listProductsResponse);

        return "favourites";
    }
}
