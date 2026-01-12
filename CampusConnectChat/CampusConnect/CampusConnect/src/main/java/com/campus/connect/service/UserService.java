package com.campus.connect.service;

import com.campus.connect.model.User;
import com.campus.connect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public String registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return "Registration failed: Email already registered.";
        }

        // Updated validation for testing
        String email = user.getEmail();
        if (email == null || (!email.endsWith("@university.edu") && !email.endsWith("@gmail.com"))) {
            return "Registration failed: Use @university.edu or @gmail.com.";
        }

        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("STUDENT");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setVerified(true);

        System.out.println("Saving user to DB: " + user.getEmail());
        userRepository.save(user);
        return "User registered successfully!";
    }

    public User loginUser(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }
}