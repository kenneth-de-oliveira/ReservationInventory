package com.example.adapter.out.client;

import feign.Headers;
import feign.RequestLine;
import com.example.adapter.out.client.dto.EmailDTO;

public interface EmailServiceClient {

    @RequestLine("POST")
    @Headers("Content-Type: application/json")
    void send(EmailDTO emailDTO);

}