package com.project.SQLpro.service;

import java.io.IOException;

import javax.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmailWithAttachment(String toEmail, String subject, String body, MultipartFile file) throws MessagingException, IOException {
        // 1️⃣ Create a new email message
       try {
    	   MimeMessage message = mailSender.createMimeMessage();
           MimeMessageHelper helper = new MimeMessageHelper(message, true);

           //  Set email properties
           helper.setTo(toEmail); // Set recipient's email
           helper.setSubject(subject); // Set subject
           helper.setText(body); // Set email body

           //  Attach CSV file
           helper.addAttachment(file.getOriginalFilename(), new ByteArrayResource(file.getBytes()));

           // Send the email
           mailSender.send(message);
       }catch(Exception e) {
    	   
    	  e.printStackTrace();
       }
    }
}