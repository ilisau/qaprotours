package com.solvd.qaprotours.web;

import com.solvd.qaprotours.service.property.MailProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Properties;

/**
 * @author Ermakovich Kseniya
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig {

    private final MailProperties mailProperties;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailProperties.getHost());
        mailSender.setPort(mailProperties.getPort());

        mailSender.setUsername(mailProperties.getUsername());
        mailSender.setPassword(mailProperties.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", mailProperties.getProperties().getProperty("mail.transport.protocol"));
        props.put("mail.smtp.auth", mailProperties.getProperties().getProperty("mail.smtp.auth"));
        props.put("mail.smtp.starttls.enable", mailProperties.getProperties().getProperty("mail.smtp.starttls.enable"));
        props.put("mail.debug", mailProperties.getProperties().getProperty("mail.debug"));

        return mailSender;
    }

}
