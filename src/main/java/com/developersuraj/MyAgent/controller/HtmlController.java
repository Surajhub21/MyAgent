package com.developersuraj.MyAgent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@CrossOrigin(origins = "*")
public class HtmlController {

    @GetMapping("/")
    public String redirectToSwagger() {
        return "redirect:/swagger-ui.html"; // Redirect to Swagger UI
    }

}