package io.bookwise.adapters.out.client.impl;

import io.bookwise.adapters.out.client.SmtpMailMessageClient;
import io.bookwise.adapters.out.mail.MailMessage;
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
public class SmtpMailMessageClientImpl implements SmtpMailMessageClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmtpMailMessageClientImpl.class);

    private final JavaMailSender javaMailSender;

    @Override
    public void sendMail(MailMessage mailMessage) {
        try {
            var message = buildSimpleMailMessage(mailMessage);
            javaMailSender.send(message);
            log.info("Email sent to: {}", mailMessage.getTo());
        } catch (Exception ex) {
            LOGGER.error("Error sending email: {}", ex.getMessage());
            throw new RuntimeException(ex.getMessage(), ex);
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