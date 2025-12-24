package com.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.entity.User;
import com.ecommerce.repo.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository repo;

    public String register(User user) {
        repo.save(user);
        return "Registered Successfully";
    }

    public String login(String email, String password) {
        User user = repo.findByEmail(email).orElse(null);
        if (user != null && user.getPassword().equals(password))
            return "Login Success";
        return "Invalid Credentials";
    }
}

