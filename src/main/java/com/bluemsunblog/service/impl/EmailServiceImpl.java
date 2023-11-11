package com.bluemsunblog.service.impl;

import com.bluemsunblog.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
@Service
public class EmailServiceImpl implements EmailService {
        @Autowired
        private JavaMailSender mailSender;

        @Value("${spring.mail.username}")
        private String from;

        /**
         * 发送邮件
         *
         * @param to      收件人邮箱
         * @param subject 邮件主题
         * @param content 邮件内容
         */
        @Override
        public void sendMail(String to, String subject, String content) throws MessagingException {
            // 创建邮件消息
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            // 发送邮件
            mailSender.send(message);
        }
    }

