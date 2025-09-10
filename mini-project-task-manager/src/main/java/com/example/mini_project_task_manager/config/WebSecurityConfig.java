package com.example.mini_project_task_manager.config;

import com.example.mini_project_task_manager.filter.JwtAuthenticationFilter;
import com.fasterxml.jackson.core.io.JsonEOFException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    
}
