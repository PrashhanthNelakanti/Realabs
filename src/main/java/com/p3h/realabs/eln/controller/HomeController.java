package com.p3h.realabs.eln.controller;

import com.p3h.realabs.eln.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

  private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

  @GetMapping("/login")
  public String login() {
    return "login";  // renders login.html
  }

  @GetMapping({"/", "/home"})
  public String home(Model model, Principal principal) {
    logger.info("User logged in: {}", principal.getName());
    model.addAttribute("username", principal.getName());
    return "home";   // renders home.html
  }

  @GetMapping("/app-setup")
  public String appSetup(Model model, Principal principal) {
    model.addAttribute("username", principal.getName());
    model.addAttribute("isAppSetup", true); // Flag for app setup
    return "app-setup";
  }


}
