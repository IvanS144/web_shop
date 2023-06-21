package com.ip.web_shop.service;

public interface EmailService {
    void sendEmail(String subject, String text, String to);
}
