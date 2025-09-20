package com.example.adapter.out.client;

import com.example.adapter.out.client.dto.UserRequest;
import com.example.adapter.out.client.dto.UserResponse;
import feign.Headers;
import feign.RequestLine;

public interface UserManagementServiceClient {

    @RequestLine("POST")
    @Headers("Content-Type: application/json")
    UserResponse create(UserRequest userRequest);

}