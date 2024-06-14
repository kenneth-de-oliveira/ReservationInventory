package io.bookwise.adapters.out.client.impl;

import io.bookwise.adapters.out.client.SendMailMessageClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendMailMessageClientImpl implements SendMailMessageClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendMailMessageClientImpl.class);

    private final JavaMailSender javaMailSender;

    @Override
    public void sendMail(String email) {
        try {
            var message = this.createNewMessageTo(email);
            javaMailSender.send(message);
            log.info("Email sent to: {}", email);
        } catch (Exception ex) {
            LOGGER.error("Error sending email: {}", ex.getMessage());
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    private SimpleMailMessage createNewMessageTo(String email) {
        var message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Bookwise-Hexagonal - Email Confirmation");
        message.setText("Your email has been confirmed successfully!");
        return message;
    }

}