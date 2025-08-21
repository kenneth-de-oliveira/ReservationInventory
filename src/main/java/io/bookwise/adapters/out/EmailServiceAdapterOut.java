package io.bookwise.adapters.out;

import feign.FeignException;
import io.bookwise.adapters.out.client.EmailServiceClient;
import io.bookwise.adapters.out.client.dto.EmailDTO;
import io.bookwise.application.core.dto.Email;
import io.bookwise.application.core.ports.out.EmailServicePortOut;
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