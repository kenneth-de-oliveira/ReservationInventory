package com.example.adapter.out;

import feign.FeignException;
import com.example.adapter.out.client.EmailServiceClient;
import com.example.adapter.out.client.dto.EmailDTO;
import com.example.application.core.dto.Email;
import com.example.application.core.port.out.EmailServicePortOut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailServiceAdapterOut implements EmailServicePortOut {

    private final EmailServiceClient emailServiceClient;

    @Override
    public void send(Email email) {
        Stream.ofNullable(email).filter(mailValue -> Objects.nonNull(mailValue.getTo()) && Objects.nonNull(mailValue.getSubject()) && Objects.nonNull(mailValue.getText())).forEach(mailValue -> {
            try {
                log.info("Sending mail to: {}, subject: {}, text: {}", email.getTo(), email.getSubject(), email.getText());
                var emailDTO = EmailDTO.builder().to(email.getTo()).subject(email.getSubject()).text(email.getText()).build();
                emailServiceClient.send(emailDTO);
                log.info("Email sent successfully to: {}", emailDTO.getTo());
            } catch (FeignException ex) {
                log.warn("Failed to send email to: {}, subject: {}. Continuing processing.", email.getTo(), email.getSubject());
            }
        });
    }

}