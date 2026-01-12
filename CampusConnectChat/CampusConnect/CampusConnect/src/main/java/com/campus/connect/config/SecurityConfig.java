package com.campus.connect.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Necessary for AJAX POST/PUT/DELETE
                .cors(cors -> cors.configure(http)) // Enable CORS support for your @CrossOrigin annotations
                .authorizeHttpRequests(auth -> auth
                        // 1. PUBLIC HTML PAGES: Ensure all dashboard and chat files are accessible
                        .requestMatchers(
                                "/", "/signup.html", "/login.html", "/home.html",
                                "/report-item.html", "/search-items.html", "/my-claims.html",
                                "/admin-dashboard.html", "/admin1.html", "/chat-list.html",
                                "/chat.html", "/error"
                        ).permitAll()

                        // 2. STATIC ASSETS: Required for CSS/JS to load correctly
                        .requestMatchers("/static/**", "/css/**", "/js/**", "/images/**", "/uploads/**").permitAll()

                        // 3. API ENDPOINTS: Crucial for login, analytics, and messaging
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/items/**").permitAll()
                        .requestMatchers("/api/claims/**").permitAll()
                        .requestMatchers("/api/chat/**").permitAll()
                        .requestMatchers("/api/admin1/**").permitAll() // FIX: Allows the admin analytics data to flow

                        // 4. CATCH-ALL: In your current dev setup, any other request must be authenticated
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.disable()) // Using custom login.js
                .httpBasic(basic -> basic.disable());

        return http.build();
    }
}