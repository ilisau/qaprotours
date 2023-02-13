package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.domain.MailType;
import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.service.JwtService;
import com.solvd.qaprotours.service.MailService;
import com.solvd.qaprotours.web.security.jwt.JwtTokenType;
import freemarker.template.Configuration;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Lisov Ilya
 */
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final Configuration configuration;
    private final JavaMailSender mailSender;
    private final JwtService jwtService;

    @Override
    @SneakyThrows
    public void sendMail(User user, MailType mailType) {
        switch (mailType) {
            case ACTIVATION -> sendActivationMail(user);
            case PASSWORD_RESET -> sendPasswordResetMail(user);
        }
    }

    @SneakyThrows
    private void sendActivationMail(User user) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Welcome to QaproTours");
        helper.setTo(user.getEmail());
        String emailContent = getActivationEmailContent(user);
        helper.setText(emailContent, true);
        mailSender.send(mimeMessage);
    }

    @SneakyThrows
    private void sendPasswordResetMail(User user) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Restore password");
        helper.setTo(user.getEmail());
        String emailContent = getRestoreEmailContent(user);
        helper.setText(emailContent, true);
        mailSender.send(mimeMessage);
    }

    @SneakyThrows
    private String getActivationEmailContent(User user) {
        StringWriter stringWriter = new StringWriter();
        String token = jwtService.generateToken(JwtTokenType.ACTIVATION, user);
        Map<String, Object> model = new HashMap<>();
        model.put("name", user.getName() + " " + user.getSurname());
        model.put("link", "http://localhost:8080/api/v1/auth/register/confirm?token=" + token);
        configuration.getTemplate("activation.ftlh")
                .process(model, stringWriter);
        return stringWriter.getBuffer()
                .toString();
    }

    @SneakyThrows
    private String getRestoreEmailContent(User user) {
        StringWriter stringWriter = new StringWriter();
        String token = jwtService.generateToken(JwtTokenType.RESET, user);
        Map<String, Object> model = new HashMap<>();
        model.put("name", user.getName() + " " + user.getSurname());
        model.put("link", "http://localhost:8080/api/v1/auth/password/restore?token=" + token);
        configuration.getTemplate("restore.ftlh")
                .process(model, stringWriter);
        return stringWriter.getBuffer()
                .toString();
    }

}
