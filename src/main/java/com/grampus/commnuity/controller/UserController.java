package com.grampus.commnuity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    @GetMapping("/join")
    public String joinPage(Model model){
//        model.addAttribute("userJoinForm", new userJoinForm());
        return "/users/join";
    }
}
