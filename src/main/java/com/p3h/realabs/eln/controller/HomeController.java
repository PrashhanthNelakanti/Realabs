package com.p3h.realabs.eln.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {
  @GetMapping("/login")
  public String login() {
    return "login";  // renders login.html
  }

  @GetMapping({"/", "/home"})
  public String home(Model model, Principal principal) {
    model.addAttribute("username", principal.getName());
    return "home";   // renders home.html
  }
}
