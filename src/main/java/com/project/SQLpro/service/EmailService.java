package com.project.SQLpro.service;

import java.io.IOException;
import javax.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender; 
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {
	
    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender; 
    }

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

  

    public void sendEmailWithAttachment(String toEmail, String subject, String body, MultipartFile file) throws MessagingException, IOException {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body);

            String fileName = file.getOriginalFilename() != null ? file.getOriginalFilename() : "attachment.csv";
            helper.addAttachment(fileName, new ByteArrayResource(file.getBytes()));

            mailSender.send(message);
            logger.info("Email sent successfully to {}", toEmail);
        } catch (Exception e) {
            logger.error("Error sending email", e);
        }
    }

    public ResponseEntity<String> sendEmail(String recipientEmail, MultipartFile file) {
        try {
            if (file.isEmpty()) {
                logger.warn("File upload failed: No file provided.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file uploaded.");
            }

            String fileName = file.getOriginalFilename() != null ? file.getOriginalFilename() : "unknown_file.csv";
            logger.info("📂 Received file: {}", fileName);
            logger.info("📩 Sending email to: {}", recipientEmail);

            sendEmailWithAttachment(recipientEmail, 
                    "SQL Results Export", 
                    "Please find the attached CSV file with SQL query results.", 
                    file);

            return ResponseEntity.ok("Email sent successfully with file: " + fileName);
        } catch (MessagingException | IOException e) {
            logger.error("Error sending email", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error sending email: " + e.getMessage());
        }
    }
}
