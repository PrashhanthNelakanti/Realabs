// src/main/java/com/p3h/realabs/eln/controller/UserController.java
package com.p3h.realabs.eln.controller.rest;


import com.p3h.realabs.eln.model.RoleEntity;
import com.p3h.realabs.eln.model.UserEntity;
import com.p3h.realabs.eln.model.dto.CreateUserRequest;
import com.p3h.realabs.eln.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/users")
    public ResponseEntity<?> addUser(@RequestBody CreateUserRequest userDto) {
        logger.info("{}",userDto);
        Set<RoleEntity> roles = userDto.getRoles().stream()
                .map(roleName -> {
                    RoleEntity role = new RoleEntity();
                    role.setName(roleName);
                    return role;
                }).collect(Collectors.toSet());

        UserEntity user = new UserEntity();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword()); // You should encode this
        user.setEnabled(userDto.isEnabled());
        user.setRoles(roles);

        return userService.addUser(user);
    }

}
