package com.example.courseworkLuchnetskyi.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConsoleController {

    @GetMapping({"/", "/console"})
    public String console() {
        return "forward:/hotel-console.html";
    }
}
