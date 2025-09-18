package com.example.mini_project_task_manager.common.errors;

public record FieldErrorItem(
        String field,
        String rejected,
        String message
) {}
