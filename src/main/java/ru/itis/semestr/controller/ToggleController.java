package ru.itis.semestr.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.semestr.config.security.UserPrincipal;
import ru.itis.semestr.entity.UserEntity;
import ru.itis.semestr.service.FavouritesService;

@Hidden
@Controller
@RequestMapping("/web")
@RequiredArgsConstructor
public class ToggleController {
    private final FavouritesService favouritesService;

    @PostMapping("/toggleProduct")
    public String toggleProduct(@RequestParam("productId") Long productId,
                                @RequestParam("isFavorite") boolean isFavorite,
                                @AuthenticationPrincipal UserPrincipal principal) {
        UserEntity user = principal.getUser();
        Long userId = user.getId();

        if (!isFavorite) {
            favouritesService.addFavourite(userId, productId);
        } else {
            favouritesService.deleteFavourite(userId, productId);
        }

        return "redirect:products";
    }

    @PostMapping("/toggleFavorite")
    public String toggleFavorite(@RequestParam("productId") Long productId,
                                 @RequestParam("isFavorite") boolean isFavorite,
                                 @AuthenticationPrincipal UserPrincipal principal) {
        UserEntity user = principal.getUser();
        Long userId = user.getId();

        if (!isFavorite) {
            favouritesService.addFavourite(userId, productId);
        } else {
            favouritesService.deleteFavourite(userId, productId);
        }

        return "redirect:favourites";
    }
}
