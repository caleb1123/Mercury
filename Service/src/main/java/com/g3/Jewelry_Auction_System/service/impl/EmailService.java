package com.g3.Jewelry_Auction_System.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    public void sendNewMail(String to, String subject, String body,String fullname) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body.replace("{recipient_email}", fullname), true); // Replace {recipient_email} with actual recipient email

        mailSender.send(message);
    }
    public void sendResetPasswordEmail(String to, String otp,String fullname) throws MessagingException {
        String subject = "Reset Your Password - Mercury Jewelry Auction";
        String body = "<html>" +
                "<body>" +
                "<h2 style=\"color: #0D6EFD;\">Reset Your Password</h2>" +
                "<p>Dear " +  fullname +",</p>" +
                "<p>We received a request to reset your password for the Mercury Jewelry Auction account associated with this email address. If you did not request this change, you can ignore this email.</p>" +
                "<p>To reset your password, please use the following OTP code:</p>" +
                "<h3 style=\"color: #0D6EFD;\">" + otp + "</h3>" +
                "<p>This OTP code will expire in 15 minutes.</p>" +
                "<p>Thank you for using Mercury Jewelry Auction!</p>" +
                "<p>Best regards,<br/>Mercury</p>" +
                "</body>" +
                "</html>";

        sendNewMail(to, subject, body,fullname);
    }
}
