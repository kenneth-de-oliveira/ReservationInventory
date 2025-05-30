package io.bookwise.adapters.out.mail;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class MailMessage {
    private String to;
    private String subject;
    private String text;
}