package com.example.mini_project_task_manager.service.impl;

import com.example.mini_project_task_manager.dto.Mail.MailRequest;
import com.example.mini_project_task_manager.provider.JwtProvider;
import com.example.mini_project_task_manager.service.MailService;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;
    private JwtProvider jwtProvider;

    public MailServiceImpl(JavaMailSender javaMailSender, JwtProvider jwtProvider) {
        this.javaMailSender = javaMailSender;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void sendEmail(MailRequest.@Valid SendMail req) {
        return;
    }

    @Override
    public void verifyEmail(String token) {
        return;
    }

//    private MimeMessage


}
