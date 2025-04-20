// src/main/java/com/p3h/realabs/eln/service/CustomUserDetailsService.java
package com.p3h.realabs.eln.service;

import com.p3h.realabs.eln.controller.HomeController;
import com.p3h.realabs.eln.model.RoleEntity;
import com.p3h.realabs.eln.model.UserEntity;
import com.p3h.realabs.eln.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepo;

  private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

  @Override
  public UserDetails loadUserByUsername(String username)
          throws UsernameNotFoundException {
    UserEntity user = userRepo.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

    logger.info("from CustomUserDetailsService{}", user);

    List<GrantedAuthority> authorities = new ArrayList<>();
    for (RoleEntity role : user.getRoles()) {
      authorities.add(new SimpleGrantedAuthority(role.getName()));
    }
    logger.info("from CustomUserDetailsService authorities{}", authorities);

    return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            user.isEnabled(),
            true, true, true,
            authorities
    );
  }
}
