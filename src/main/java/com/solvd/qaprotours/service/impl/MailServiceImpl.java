package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.domain.MailType;
import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.service.MailService;
import com.solvd.qaprotours.service.property.MailLinkProperties;
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
    private final MailLinkProperties mailLinkProperties;

    @Override
    @SneakyThrows
    public void sendMail(User user, MailType mailType, Map<String, Object> params) {
        switch (mailType) {
            case ACTIVATION -> sendActivationMail(user, params);
            case PASSWORD_RESET -> sendPasswordResetMail(user, params);
            case BOOKED_TOUR -> sendBookedTourMail(user, params);
            case TICKET_CANCELED -> sendTicketCanceledMail(user, params);
        }
    }

    @SneakyThrows
    private void sendActivationMail(User user, Map<String, Object> params) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Welcome to QaproTours");
        helper.setTo(user.getEmail());
        String emailContent = getActivationEmailContent(user, params);
        helper.setText(emailContent, true);
        mailSender.send(mimeMessage);
    }

    @SneakyThrows
    private void sendPasswordResetMail(User user, Map<String, Object> params) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Restore password");
        helper.setTo(user.getEmail());
        String emailContent = getRestoreEmailContent(user, params);
        helper.setText(emailContent, true);
        mailSender.send(mimeMessage);
    }

    @SneakyThrows
    private void sendBookedTourMail(User user, Map<String, Object> params) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Soon tours");
        helper.setTo(user.getEmail());
        String emailContent = getBookedTourMail(user, params);
        helper.setText(emailContent, true);
        mailSender.send(mimeMessage);
    }

    @SneakyThrows
    private void sendTicketCanceledMail(User user, Map<String, Object> params) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Ticket canceled");
        helper.setTo(user.getEmail());
        String emailContent = getTicketCanceledMail(user, params);
        helper.setText(emailContent, true);
        mailSender.send(mimeMessage);
    }

    @SneakyThrows
    private String getActivationEmailContent(User user, Map<String, Object> params) {
        StringWriter stringWriter = new StringWriter();

        Map<String, Object> model = new HashMap<>();
        model.put("name", user.getName() + " " + user.getSurname());
        model.put("link", mailLinkProperties.getActivation() + params.get("token"));
        configuration.getTemplate("activation.ftlh")
                .process(model, stringWriter);
        return stringWriter.getBuffer()
                .toString();
    }

    @SneakyThrows
    private String getRestoreEmailContent(User user, Map<String, Object> params) {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("name", user.getName() + " " + user.getSurname());
        model.put("link", mailLinkProperties.getRestore() + params.get("token"));
        configuration.getTemplate("restore.ftlh")
                .process(model, stringWriter);
        return stringWriter.getBuffer()
                .toString();
    }

    @SneakyThrows
    private String getBookedTourMail(User user, Map<String, Object> params) {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("name", user.getName() + " " + user.getSurname());
        model.put("ticket", params.get("ticket"));
        configuration.getTemplate("booked.ftlh")
                .process(model, stringWriter);
        return stringWriter.getBuffer()
                .toString();
    }

    @SneakyThrows
    private String getTicketCanceledMail(User user, Map<String, Object> params) {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("name", user.getName() + " " + user.getSurname());
        model.put("ticket", params.get("ticket"));
        configuration.getTemplate("canceled.ftlh")
                .process(model, stringWriter);
        return stringWriter.getBuffer()
                .toString();
    }

}
