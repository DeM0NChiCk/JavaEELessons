package ru.itis.semestr.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.semestr.config.AppConfig;

@Hidden
@Controller
@RequestMapping("/admin")
public class AdminNewsControllers {

    private final String url;

    public AdminNewsControllers(AppConfig appConfig) {
        this.url = appConfig.googleConfig().getUrl();
    }


    @GetMapping("/news")
    public String getMainPage() {
        return "redirect:" + url;
    }

}
