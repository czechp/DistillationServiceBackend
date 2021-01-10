package com.czechp.DistillationServiceBackend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service()
@Slf4j()
public class EmailServiceImpl implements EmailService {
    private JavaMailSender javaMailSender;

    @Autowired()
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmail(String emailAddress, String subject, String body) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(emailAddress);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);
        new Thread(() -> {
            javaMailSender.send(simpleMailMessage);
            log.info("Email sent to {}", emailAddress);
        }).start();
    }


}
