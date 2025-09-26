package com.example.mini_project_task_manager.service;

import com.example.mini_project_task_manager.dto.Mail.MailRequest;
import jakarta.validation.Valid;

public interface MailService {
    void sendEmail(MailRequest.@Valid SendMail req);
    void verifyEmail(String token);
}
