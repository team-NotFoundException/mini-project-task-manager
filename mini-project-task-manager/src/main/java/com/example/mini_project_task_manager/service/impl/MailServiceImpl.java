package com.example.mini_project_task_manager.service.impl;

import com.example.mini_project_task_manager.dto.Mail.MailRequest;
import com.example.mini_project_task_manager.provider.JwtProvider;
import com.example.mini_project_task_manager.service.MailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;
    private final JwtProvider jwtProvider;


    private MimeMessage createEmail(String email, String token) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setFrom(senderEmail);
        message.setRecipients(MimeMessage.RecipientType.TO, email);

        message.setSubject("=== [mini_project_task_manager] 이메일 인증 링크 발송 ===");

        String body = """
                    <h3>이메일 인증링크 입니다.</3>
                    <a href="http://localhost:8080/api/v1/auth/verify?token=%s">여기를 클릭하여 인증 완료해주세요.</a>
                """.formatted(token);
        message.setText(body, "UTF-8", "html");

        return  message;
    }

    @Override
    public void sendEmail(MailRequest.@Valid SendMail req) {
        try {
            String token = jwtProvider.generateJwtToken(req.email());
            MimeMessage message = createEmail(req.email(), token);

            javaMailSender.send(message);

            System.out.println("인증 이메일이 전송되었습니다.");
        } catch (MessagingException | MailException e) {
            System.out.println("이메일 전송 실패: " + e.getMessage());
            throw new RuntimeException("이메일 전송 실패", e);
        }

    }

    private MimeMessage createEmail(@NotBlank @Email String email, String token) {
    }

    @Override
    public void verifyEmail(String token) {
        String email = jwtProvider.getUsernameFromJwt(token);
        System.out.println("이메일 인증이 완료되었습니다. 이메일: " + email);
    }

}
