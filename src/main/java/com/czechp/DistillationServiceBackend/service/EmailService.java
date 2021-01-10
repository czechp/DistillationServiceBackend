package com.czechp.DistillationServiceBackend.service;

public interface EmailService {
    public void sendEmail(String emailAddress, String subject, String body);
}
