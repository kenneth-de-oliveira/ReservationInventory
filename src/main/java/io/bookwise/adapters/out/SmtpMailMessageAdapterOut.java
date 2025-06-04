package io.bookwise.adapters.out;

import io.bookwise.adapters.out.client.SmtpMailMessageClient;
import io.bookwise.adapters.out.mapper.MailMessageMapper;
import io.bookwise.application.core.dto.MailMessage;
import io.bookwise.application.core.ports.out.SmtpMailMessagePortOut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SmtpMailMessageAdapterOut implements SmtpMailMessagePortOut {

    private final SmtpMailMessageClient smtpMailMessageClient;
    private final MailMessageMapper mapper;

    @Override
    public void sendMail(MailMessage mailMessage) {
        log.info("Sending mail using SmtpMailMessageAdapterOut: {}", mailMessage);

        var mail = mapper.toMailMessage( mailMessage.getTo(),
                mailMessage.getSubject(),
                mailMessage.getText()
        ) ;

        smtpMailMessageClient.sendMail(mail);
    }

}