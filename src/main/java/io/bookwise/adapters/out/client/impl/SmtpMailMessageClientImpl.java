package io.bookwise.adapters.out.client.impl;

import io.bookwise.adapters.out.client.SmtpMailMessageClient;
import io.bookwise.application.core.dto.MailMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SmtpMailMessageClientImpl implements SmtpMailMessageClient {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendMail(MailMessage mailMessage) {
        try {
            log.info("Sending mail to: {}, subject: {}", mailMessage.getTo(), mailMessage.getSubject());
            var message = buildSimpleMailMessage(mailMessage);
            javaMailSender.send(message);
            log.info("Email sent successfully to: {}", mailMessage.getTo());
        } catch (Exception ex) {
            log.error("Error sending email to: {}, subject: {}. Error: {}", mailMessage.getTo(), mailMessage.getSubject(), ex.getMessage(), ex);
        }
    }

    private SimpleMailMessage buildSimpleMailMessage(MailMessage mailMessage) {
        return new SimpleMailMessage() {{
            setTo(mailMessage.getTo());
            setSubject(mailMessage.getSubject());
            setText(mailMessage.getText());
        }};
    }

}