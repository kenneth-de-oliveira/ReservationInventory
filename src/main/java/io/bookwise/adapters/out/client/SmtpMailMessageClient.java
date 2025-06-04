package io.bookwise.adapters.out.client;

import io.bookwise.application.core.dto.MailMessage;

public interface SmtpMailMessageClient {
    void sendMail(MailMessage mailMessage);
}