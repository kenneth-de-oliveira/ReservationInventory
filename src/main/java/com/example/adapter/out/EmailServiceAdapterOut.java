package com.example.adapter.out;

import com.example.adapter.out.client.EmailServiceClient;
import com.example.application.core.dto.Email;
import com.example.application.core.port.out.EmailServicePortOut;
import com.example.shared.mapper.EmailMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailServiceAdapterOut implements EmailServicePortOut {

    private final EmailServiceClient emailServiceClient;
    private final EmailMapper mapper;

    @Override
    public void send(Email email) {
        try {
            log.info("Sending mail to: {}, subject: {}, text: {}", email.getTo(), email.getSubject(), email.getText());
            var request = mapper.toRequest(email);
            emailServiceClient.send(request);
            log.info("Email sent successfully to: {}", request.getTo());
        } catch (FeignException ex) {
            log.warn("Failed to send email to: {}, subject: {}. Continuing processing.", email.getTo(), email.getSubject());
        }
    }

}