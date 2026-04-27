package ru.itis.semestr.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.semestr.config.security.UserPrincipal;
import ru.itis.semestr.dto.response.UserDataResponse;
import ru.itis.semestr.entity.News;
import ru.itis.semestr.entity.UserEntity;
import ru.itis.semestr.mapper.UserMapper;
import ru.itis.semestr.service.NewsService;

import java.util.List;

@Hidden
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class InfoController {

    private final UserMapper userMapper;
    private final NewsService newsService;

    @GetMapping()
    public String getGreetingPage() {
        return "basic";
    }

    @GetMapping("/web/main")
    public String getMainPage(@AuthenticationPrincipal UserPrincipal principal, Model model) {
        UserEntity user = principal.getUser();
        UserDataResponse dto = userMapper.toDto(user);

        List<News> newsList = newsService.fetchNewsFromGoogleSheets();


        model.addAttribute("user", dto);
        model.addAttribute("news", newsList);

        return "main";
    }
}
