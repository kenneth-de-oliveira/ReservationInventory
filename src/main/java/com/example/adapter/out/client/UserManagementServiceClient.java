package com.example.adapter.out.client;

import com.example.adapter.out.client.dto.UserRequest;
import com.example.adapter.out.client.dto.UserResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface UserManagementServiceClient {

    @RequestLine("POST")
    @Headers("Content-Type: application/json")
    UserResponse create(UserRequest userRequest);

    @RequestLine("GET ?document={document}")
    @Headers("Accept: application/json")
    UserResponse findByDocument(@Param("document") String document);

}