package io.bookwise.application.core.ports.out;

import io.bookwise.adapters.out.mail.MailMessage;

public interface SmtpMailMessagePortOut {
    void sendMail(MailMessage mailMessage);
}