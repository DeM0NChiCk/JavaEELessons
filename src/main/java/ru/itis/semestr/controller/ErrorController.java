package ru.itis.semestr.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Hidden
@Controller
@RequiredArgsConstructor
@RequestMapping("")
public class ErrorController {

    @GetMapping("/error-auth")
    public String getGreetingPage(@RequestParam(value = "err", required = false) String err, Model model) {
        model.addAttribute("err", err);
        return "error-auth";
    }
}
