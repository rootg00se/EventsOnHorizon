package com.deg00se.events.services.impl;

import com.deg00se.events.services.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String mailSender;

    @Override
    public void sendConfirmationMail(String toEmail, String confirmLink) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(mailSender);
        message.setSubject("Email confirmation");
        message.setTo(toEmail);
        message.setText("Please, follow this link to confirm your email: " + confirmLink);

        javaMailSender.send(message);
    }
}
