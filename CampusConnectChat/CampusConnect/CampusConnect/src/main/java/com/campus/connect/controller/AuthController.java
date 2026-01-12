package com.campus.connect.controller;

import com.campus.connect.dto.LoginRequest;
import com.campus.connect.model.User;
import com.campus.connect.repository.UserRepository;
import com.campus.connect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // ✅ Added Import
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // ✅ Inject the Encoder

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        String result = userService.registerUser(user);
        if (result.contains("successfully")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("Login attempt for: " + loginRequest.getEmail());

        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // ✅ Use .matches() for BCrypt verification
            // Format: .matches(rawPassword, hashedByDB)
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {

                Map<String, Object> response = new HashMap<>();
                response.put("role", user.getRole());
                response.put("name", user.getName()); // This fixes the "Hello, User" issue
                response.put("email", user.getEmail());
                response.put("token", "dummy-jwt-token");

                return ResponseEntity.ok(response);
            } else {
                System.out.println("BCrypt Match Failed: Passwords do not match.");
            }
        } else {
            System.out.println("User not found in Database: " + loginRequest.getEmail());
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
    }
}