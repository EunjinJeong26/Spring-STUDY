package com.example.hyeju.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller

public class FirstController {

    @GetMapping("/bye")
    public String seeYouNext(Model model) {
        model.addAttribute("nickname","혜주");
        return "goodbye";
    }

    @GetMapping("/hi")
    public String niceToMeetYou(Model model) {
        model.addAttribute("username","혜주");
        return "greetings";
    }
}
