package com.p3h.realabs.eln.service;


import com.p3h.realabs.eln.model.RoleEntity;
import com.p3h.realabs.eln.model.UserEntity;
import com.p3h.realabs.eln.repository.RoleRepository;
import com.p3h.realabs.eln.repository.UserRepository;
import com.p3h.realabs.eln.exceptions.UserAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo,
                       PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<UserEntity> addUser(UserEntity user) {

        Set<RoleEntity> incoming = user.getRoles();

        // 2) Prepare a container for the “resolved” (managed) roles
        Set<RoleEntity> resolved = new HashSet<>();

        logger.info("Adding user: {}", user.getUsername());

        // Check for existing user
        if (userRepo.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists: " + user.getUsername());
        }
        if(incoming == null || incoming.isEmpty()){
            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setName("ROLE_USER");
            resolved.add(roleEntity);
            user.setRoles(resolved);
            user.setEnabled(true);
            }
             incoming = user.getRoles().stream()
                    .map(role -> roleRepository.findRoleEntitiesByName(role.getName())
                            .orElseThrow(() -> new RuntimeException("Role not found: " + role)))
                    .collect(Collectors.toSet());


        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setEnabled(user.isEnabled());
        userEntity.setRoles(incoming);

        // Save to DB
        UserEntity savedUser = userRepo.save(userEntity);
        logger.info("User saved: {}", savedUser);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    public Optional<UserEntity> getUserByName(String username) {
        return userRepo.findByUsername(username);
    }

    public Optional<UserEntity> getUserById(Long id) {
        logger.info("Fetching user by id: {}", id);
        return userRepo.findById(id);
    }

    public ResponseEntity<List<UserEntity>> getAllUsers() {
        List<UserEntity> users = userRepo.findAll();
        logger.info("Retrieved {} users", users.size());
        return ResponseEntity.ok(users);
    }
}
