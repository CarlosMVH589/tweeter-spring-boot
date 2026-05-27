package com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.Instrumento;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.User;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.repository.RoleRepository;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.repository.UserRepository;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.security.jwt.JwtUtils;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    
    @PutMapping("/{id}")
    public User editUser(@Valid @RequestBody User user) {
        
        System.out.println(user);

        User existingUser = getValidUser(user.getUsername());
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(encoder.encode(user.getPassword()));
        existingUser.setRoles(user.getRoles());
        userRepository.save(existingUser);
        return existingUser;
    }

    private User getValidUser(String userId) {
        Optional<User> userOpt = userRepository.findByUsername(userId);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found");
        }
        return userOpt.get();
    }
}

