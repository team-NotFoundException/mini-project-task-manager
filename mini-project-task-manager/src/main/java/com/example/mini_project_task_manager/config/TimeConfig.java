package com.example.mini_project_task_manager.config;

import jakarta.annotation.PostConstruct;

import java.util.TimeZone;

import org.springframework.context.annotation.Configuration;

@Configuration
public class TimeConfig {
    @PostConstruct
    public void setDefaultTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}
