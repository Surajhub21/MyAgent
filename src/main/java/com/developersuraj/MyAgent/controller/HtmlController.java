package com.developersuraj.MyAgent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlController {

    @GetMapping("/")
    public String redirectToSwagger() {
        return "redirect:/swagger-ui.html"; // Redirect to Swagger UI
    }

}