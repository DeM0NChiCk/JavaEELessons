package ru.itis.semestr.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.semestr.config.security.UserPrincipal;
import ru.itis.semestr.dto.response.ListProductsResponse;
import ru.itis.semestr.entity.UserEntity;
import ru.itis.semestr.service.FavouritesService;

@Hidden
@Controller
@RequestMapping("/web/favourites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavouritesService favouritesService;

    @GetMapping
    public String getFavouritesPage(@AuthenticationPrincipal UserPrincipal principal, Model model) {
        UserEntity user = principal.getUser();

        ListProductsResponse listProductsResponse = favouritesService.getAllFavourites(user.getId());

        model.addAttribute("favourites", listProductsResponse);

        return "favourites";
    }
}
