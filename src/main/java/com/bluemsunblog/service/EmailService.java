package com.bluemsunblog.service;

import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
@Transactional
public interface EmailService {
    void sendMail(String to, String subject, String content) throws MessagingException;
}
