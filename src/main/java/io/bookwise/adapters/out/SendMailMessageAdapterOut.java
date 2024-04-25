package io.bookwise.adapters.out;

import io.bookwise.adapters.out.client.SendMailMessageClient;
import io.bookwise.application.core.ports.out.SendMailMessagePortOut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendMailMessageAdapterOut implements SendMailMessagePortOut {

    private final SendMailMessageClient sendMailMessageClient;

    @Override
    public void send(String email) {
        log.info("Sending email to: {}", email);
        sendMailMessageClient.sendMail(email);
    }

}