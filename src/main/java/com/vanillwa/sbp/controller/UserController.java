package com.vanillwa.sbp.controller;

import com.vanillwa.sbp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/user")
    public String getUser(){
        System.out.println("User connected");
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return username;
    }
}
