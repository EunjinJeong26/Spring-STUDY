package com.example.eunjin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class FirstController {

    @GetMapping("/hi")
    public String nieToMeetYou(Model model){
        model.addAttribute("username", "eunjin");
        return "greetings";
    }

    @GetMapping("/bye")
    public String seeYouNext(Model model){
        model.addAttribute("nickname", "정은진");
        return "goodbye";
    }
}
