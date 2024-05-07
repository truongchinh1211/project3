package com.example.project3.service;

import com.example.project3.security.MailConfig;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class MailService {
    @Autowired
    private MailConfig mailConfig;

    @Value("${spring.mail.username}")
    String username;

    public void send(String toEmail, String subject, String body) throws MessagingException, UnsupportedEncodingException {
        JavaMailSender javaMailSender = mailConfig.getJavaMailSender();
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(username, "Hieu");
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(body);

        javaMailSender.send(message);
    }
}
