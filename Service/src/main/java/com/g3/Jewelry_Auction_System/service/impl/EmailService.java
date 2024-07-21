package com.g3.Jewelry_Auction_System.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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

    public void sendPreliminaryValuationCompleteEmail(String to, String jewelryName, String preliminaryPrice, LocalDate expectedEvaluationDate, String fullname) throws MessagingException {
        String subject = "Preliminary Valuation Complete - Mercury Jewelry Auction";
        String body = "<html>" +
                "<body>" +
                "<h2 style=\"color: #0D6EFD;\">Preliminary Valuation Complete</h2>" +
                "<p>Dear " + fullname + ",</p>" +
                "<p>We are pleased to inform you that the preliminary valuation for your product, <strong>" + jewelryName + "</strong>, has been completed. The estimated price is <strong>" + preliminaryPrice + "</strong>.</p>" +
                "<p>Please send your product to Mercury Company by <strong>" + expectedEvaluationDate + "</strong> for the final evaluation.</p>" +
                "<p>Thank you for choosing Mercury Jewelry Auction for your valuation needs. We look forward to receiving your product.</p>" +
                "<p>Best regards,<br/>Mercury</p>" +
                "</body>" +
                "</html>";

        sendNewMail(to, subject, body, fullname);
    }
    public void sendAuctionWinnerEmail(String to, String auctionName, double winningBid, String fullname) throws MessagingException {
        String subject = "Congratulations - You've Won the Auction - Mercury Jewelry Auction";
        String body = "<html>" +
                "<body>" +
                "<h2 style=\"color: #0D6EFD;\">Congratulations on Winning the Auction!</h2>" +
                "<p>Dear " + fullname + ",</p>" +
                "<p>We are pleased to inform you that you have won the auction for <strong>" + auctionName + "</strong>. Your winning bid was <strong>" + winningBid + "</strong>.</p>" +
                "<p>Please proceed to payment and shipping arrangements. If you have any questions, feel free to contact us.</p>" +
                "<p>Thank you for participating in Mercury Jewelry Auction!</p>" +
                "<p>Best regards,<br/>Mercury</p>" +
                "</body>" +
                "</html>";

        sendNewMail(to, subject, body, fullname);
    }

    public void sendOTPtoActiveAccount(String to, String otp,String fullname) throws MessagingException {
        String subject = "OTP to active account - Mercury Jewelry Auction";
        String body = "<html>" +
                "<body>" +
                "<h2 style=\"color: #0D6EFD;\">OTP code</h2>" +
                "<p>Dear " +  fullname +",</p>" +
                "<p>We received a request to active your account for the Mercury Jewelry Auction account associated with this email address. If you did not request this change, you can ignore this email.</p>" +
                "<p>To active your account, please use the following OTP code:</p>" +
                "<h3 style=\"color: #0D6EFD;\">" + otp + "</h3>" +
                "<p>This OTP code will expire in 15 minutes.</p>" +
                "<p>Thank you for using Mercury Jewelry Auction!</p>" +
                "<p>Best regards,<br/>Mercury</p>" +
                "</body>" +
                "</html>";
        sendNewMail(to, subject, body,fullname);
    }
    public void sendApologyEmail(String to, String auctionName, String fullname) throws MessagingException {
        String subject = "Apology for the Auction Issue - Mercury Jewelry Auction";
        String body = "<html>" +
                "<body>" +
                "<h2 style=\"color: #DC3545;\">Apology for the Auction Issue</h2>" +
                "<p>Dear " + fullname + ",</p>" +
                "<p>We regret to inform you that there was an issue with the auction for <strong>" + auctionName + "</strong>.</p>" +
                "<p>We sincerely apologize for any inconvenience caused. We are working to resolve the issue and will notify you once the auction is back on track.</p>" +
                "<p>Thank you for your understanding and continued support of Mercury Jewelry Auction.</p>" +
                "<p>Best regards,<br/>Mercury</p>" +
                "</body>" +
                "</html>";

        sendNewMail(to, subject, body, fullname);
    }


}
