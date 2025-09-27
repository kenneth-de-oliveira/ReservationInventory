package com.example.adapter.in;

import com.example.application.core.port.in.CreateUserPortIn;
import com.example.application.core.port.in.FindUserPortIn;
import com.example.shared.dto.UserRequest;
import com.example.shared.dto.UserResponse;
import com.example.shared.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserResourceAdapterIn {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserResourceAdapterIn.class);

    private final CreateUserPortIn createUserPortIn;
    private final FindUserPortIn findUserPortIn;
    private final UserMapper mapper;

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest userRequest) {
        try {
            log.info("Creating user: {}", userRequest);
            var user = mapper.toDomain(userRequest);
            user = createUserPortIn.create(user);
            var userResponse = mapper.toResponse(user);
            ResponseEntity<UserResponse> responseEntity = ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{document}")
                    .buildAndExpand(userRequest.getDocument()).toUri()).body(userResponse);
            log.info("Created user: {}", userRequest);
            return responseEntity;

        } catch (Exception ex) {
            LOGGER.error("Error creating userRequest: {}", ex.getMessage());
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    @GetMapping
    public ResponseEntity<UserResponse> findByDocument(@RequestParam String document) {
        try {
            log.info("Finding user by document: {}", document);
            var user = findUserPortIn.findByDocument(document);
            var userResponse = mapper.toResponse(user);
            log.info("Found user: {}", user);
            return ResponseEntity.ok(userResponse);
        } catch (Exception ex) {
            LOGGER.error("Error finding user by document {}: {}", document, ex.getMessage());
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

}