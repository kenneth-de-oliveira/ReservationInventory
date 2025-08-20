package io.bookwise.application.core.ports.out;

import io.bookwise.application.core.dto.Email;

public interface EmailServicePortOut {
    void sendEmail(Email email);
}