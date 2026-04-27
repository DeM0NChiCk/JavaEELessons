package ru.itis.semestr.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Hidden
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminInfoController {

    @GetMapping("/main")
    public String getMainPage() {
        return "adminMain";
    }
}
