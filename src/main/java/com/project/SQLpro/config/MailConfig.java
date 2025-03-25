package com.project.SQLpro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        
        // ✅ Configure SMTP settings
        mailSender.setHost("smtp.gmail.com");  // Change this if using another provider
        mailSender.setPort(587);
        mailSender.setUsername("aparnasaini6@gmail.com"); // Your email
        mailSender.setPassword("kbab zmnu jbrz qbqz"); // Use an app password, not your actual password

        // ✅ Set additional mail properties
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true"); // Enables detailed logging for debugging

        mailSender.setJavaMailProperties(props);
        return mailSender;
    }
}
