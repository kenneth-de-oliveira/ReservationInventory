package io.bookwise.adapters.out.mapper;

import io.bookwise.adapters.out.mail.MailMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MailMessageMapper {

    MailMessage toMailMessage( String to, String subject, String text );
}