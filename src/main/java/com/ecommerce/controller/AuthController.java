package com.ecommerce.controller;

import com.ecommerce.entity.User;
import com.ecommerce.repo.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // ✅ REGISTER USER / ADMIN
    @PostMapping("/register")
    public User register(@RequestBody User user) {

        // default role if not provided
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }

        return userRepository.save(user);
    }

    // ✅ LOGIN USER / ADMIN
    @PostMapping("/login")
    public User login(@RequestBody User request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // ✅ return full user (id, role used by frontend)
        return user;
    }
}
