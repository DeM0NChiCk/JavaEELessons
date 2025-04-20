package ru.itis.lessonservlet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("")
public class InfoController {

    @GetMapping()
    public String getGreetingPage() {
        return "basic";
    }
}
