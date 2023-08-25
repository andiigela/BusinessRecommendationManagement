package com.ubt.andi.businessrecommendation.services;
import com.ubt.andi.businessrecommendation.repositories.ApplicationUserRepository;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmailService{
    private final JavaMailSender javaMailSender;
   // private ApplicationUserRepository userRepository;
    @Autowired
    public EmailService(ApplicationUserRepository userRepository,JavaMailSender javaMailSender){
        this.javaMailSender=javaMailSender;
     //   this.userRepository=userRepository;
    }
    public EmailService(JavaMailSender javaMailSender){
        this.javaMailSender=javaMailSender;
    }
    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
    public void sendEmail(String to,String generatedToken){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("andiigela@hotmail.com");
        message.setTo(to);
        message.setSubject("Email Confirmation");
        String confirmationToken = generatedToken;
        String confirmationLink = "https://localhost:8080/confirm?token=" + confirmationToken;
        message.setText("Click the Link to activate your account: " + confirmationLink);
        javaMailSender.send(message);
    }
}
