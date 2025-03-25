package com.project.SQLpro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import com.project.SQLpro.service.EmailService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;

import javax.mail.MessagingException;
import java.io.IOException;
 import com.project.SQLpro.config.*;

@RestController
//@RequestMapping("/api/email")
public class EmailController {
	
	
	

    @Autowired
    private EmailService emailService;
    @PostMapping("/api/email")
    public ResponseEntity<String> sendEmail(
    		@Valid
    		@Email
            @RequestParam("email") String recipientEmail, // Get email from request
            @RequestParam("file") MultipartFile file) {
    	
    	
    	ResponseEntity<String> re= emailService.sendEmail(recipientEmail,file);
    			return re; 
    }
}

