package com.example.adapter.out.client;

import com.example.shared.dto.EmailRequest;
import feign.Headers;
import feign.RequestLine;

public interface EmailServiceClient {

    @RequestLine("POST")
    @Headers("Content-Type: application/json")
    void send(EmailRequest emailRequest);

}