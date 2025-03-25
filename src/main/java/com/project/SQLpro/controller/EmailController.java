package com.project.SQLpro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import com.project.SQLpro.service.EmailService;

import javax.mail.MessagingException;
import java.io.IOException;
 

@RestController
//@RequestMapping("/api/email")
public class EmailController {
	
	

    @Autowired
    private EmailService emailService;
    @PostMapping("/api/email")
    public ResponseEntity<String> sendEmail(
            @RequestParam("email") String recipientEmail, // Get email from request
            @RequestParam("file") MultipartFile file) {
        try {
        	// ✅ Check if file is empty
            if (file.isEmpty()) {
            	System.out.println("HttpStatus.BAD_REQUEST");
            	System.out.println("trying to chnge commit");
            	System.out.println("heyyy");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file uploaded.");
                
            }
            // ✅ Get file details
            String fileName = file.getOriginalFilename();
            byte[] fileBytes = file.getBytes();

            System.out.println("📂 Received file: " + fileName);
            System.out.println("📩 Sending email to: " + recipientEmail);
            
            emailService.sendEmailWithAttachment(recipientEmail, // Use dynamic email
                  "SQL Results Export",
                  "Please find the attached CSV file with SQL query results.",
                  file);
            
            // ✅ You can save the file or process it (e.g., store in DB, send via email)

            return ResponseEntity.ok("File received successfully: " + fileName);
            
            

//            emailService.sendEmailWithAttachment(recipientEmail, // Use dynamic email
//                    "SQL Results Export",
//                    "Please find the attached CSV file with SQL query results.",
//                    file);
//            return ResponseEntity.ok("Email sent successfully!");
        } catch (MessagingException | IOException e) {
            return ResponseEntity.status(500).body("Error sending email: " + e.getMessage());
        }
    }
}

//try {
//    
//   
//
//   
//
//} catch (IOException e) {
//    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing file.");
//}
//}
