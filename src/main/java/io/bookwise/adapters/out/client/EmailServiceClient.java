package io.bookwise.adapters.out.client;

import feign.Headers;
import feign.RequestLine;
import io.bookwise.adapters.out.client.dto.EmailRequest;

public interface EmailServiceClient {

    @RequestLine("POST")
    @Headers("Content-Type: application/json")
    void sendEmail(EmailRequest emailRequest);

}