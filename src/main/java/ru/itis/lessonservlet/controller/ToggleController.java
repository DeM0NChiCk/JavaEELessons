package ru.itis.lessonservlet.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.lessonservlet.dto.response.UserDataResponse;
import ru.itis.lessonservlet.service.FavouritesService;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class ToggleController {
    private final FavouritesService favouritesService;

    @PostMapping("/toggleProduct")
    public String toggleProduct(@RequestParam("productId") Long productId,
                                @RequestParam("isFavorite") boolean isFavorite,
                                HttpSession session) {
        UserDataResponse user = (UserDataResponse) session.getAttribute("user");
        Long userId = user.getId();

        if (!isFavorite) {
            favouritesService.addFavorite(userId, productId);
        } else {
            favouritesService.deleteFavorite(userId, productId);
        }

        return "redirect:/products";
    }

    @PostMapping("/toggleFavorite")
    public String toggleFavorite(@RequestParam("productId") Long productId,
                                 @RequestParam("isFavorite") boolean isFavorite,
                                 HttpSession session) {
        UserDataResponse user = (UserDataResponse) session.getAttribute("user");
        Long userId = user.getId();

        if (!isFavorite) {
            favouritesService.addFavorite(userId, productId);
        } else {
            favouritesService.deleteFavorite(userId, productId);
        }

        return "redirect:/favourites";
    }
}
