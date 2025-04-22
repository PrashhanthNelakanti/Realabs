package com.p3h.realabs.eln.controller.ui;

import com.p3h.realabs.eln.exceptions.UserAlreadyExistsException;
import com.p3h.realabs.eln.model.UserEntity;
import com.p3h.realabs.eln.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller

public class BaseRenderController {

  private static final Logger logger = LoggerFactory.getLogger(BaseRenderController.class);

  private final UserService userService;

  public BaseRenderController(UserService userService) {
    this.userService = userService;
  }
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

  @GetMapping("/register")
  public String showRegisterForm() {
    return "register"; // renders register.html
  }

  @PostMapping("/register")
  public String doRegister(@RequestParam String username,
                           @RequestParam String password,
                           Model model) {
    UserEntity userEntity = new UserEntity();
    userEntity.setUsername(username);
    userEntity.setPassword(password);
    try {
      userService.addUser(userEntity);
      model.addAttribute("successMessage", "Registration successful! Please log in.");
      return "register";
    } catch (UserAlreadyExistsException e) {
      model.addAttribute("errorMessage", e.getMessage());
      return "register";
    }
  }


}
