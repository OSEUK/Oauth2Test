package com.example.retrocam.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminTestController {

    @GetMapping("/admin")
    public String adminPage(){

        return "role check";
    }
}
