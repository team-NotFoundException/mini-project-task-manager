package com.example.mini_project_task_manager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;


@Configuration
public class MailConfig {
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;

    @Bean
    // javaMailSender을 Bean 생성 할때 없으면 Spring Boot 안에 있는 자체 내의 코드 가 사용되어 작동은 되지만 우리가 실질적으로 사용하기에는 수동으로 지정해줘야한다
    // EX: build.gradle (Spring Web Email 기능 구현 스타터implementation 'org.springframework.boot:spring-boot-starter-mail')
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);


        Properties props = mailSender.getJavaMailProperties();

        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}