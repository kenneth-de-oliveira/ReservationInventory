package io.bookwise.application.core.ports.out;

import io.bookwise.application.core.dto.MailMessage;

public interface SmtpMailMessagePortOut {
    void sendMail(MailMessage mailMessage);
}