package io.bookwise.adapters.out;

import io.bookwise.adapters.out.client.SmtpMailMessageClient;
import io.bookwise.application.core.dto.MailMessage;
import io.bookwise.application.core.ports.out.SmtpMailMessagePortOut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmtpMailMessageAdapterOut implements SmtpMailMessagePortOut {

    private final SmtpMailMessageClient smtpMailMessageClient;

    @Override
    public void sendMail(MailMessage mailMessage) {
        smtpMailMessageClient.sendMail(mailMessage);
    }

}