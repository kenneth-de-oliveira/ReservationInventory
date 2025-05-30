package io.bookwise.adapters.out.client;

import io.bookwise.adapters.out.mail.MailMessage;

public interface SmtpMailMessageClient {
    void sendMail(MailMessage mailMessage);
}