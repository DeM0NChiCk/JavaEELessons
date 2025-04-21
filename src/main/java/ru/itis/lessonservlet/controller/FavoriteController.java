package ru.itis.lessonservlet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/favourites")
@RequiredArgsConstructor
public class FavoriteController {

    @GetMapping()
    public String GetFavouritesPage() {
        return "favourites";
    }
}
