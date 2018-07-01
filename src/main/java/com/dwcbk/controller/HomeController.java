package com.dwcbk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Simple controller that dispatches to the home page (templates/index.ftl)
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    public String home(Model model) {
        return "index";
    }
}