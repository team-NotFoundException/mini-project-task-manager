package com.example.mini_project_task_manager.service.impl;

import com.example.mini_project_task_manager.provider.JwtProvider;
import com.example.mini_project_task_manager.service.MailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;

public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;
    private final JwtProvider jwtProvider;

    private MimeMessage


}
