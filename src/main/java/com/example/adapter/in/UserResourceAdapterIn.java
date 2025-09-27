package com.example.adapter.in;

import com.example.application.core.domain.User;
import com.example.application.core.port.in.CreateUserPortIn;
import com.example.application.core.port.in.FindUserPortIn;
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

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        try {
            log.info("Creating user: {}", user);
            user = createUserPortIn.create(user);
            ResponseEntity<User> responseEntity = ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{document}")
                    .buildAndExpand(user.getDocument()).toUri()).body(user);
            log.info("Created user: {}", user);
            return responseEntity;

        }catch (Exception ex){
            LOGGER.error("Error creating user: {}", ex.getMessage());
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    @GetMapping
    public ResponseEntity<User> findByDocument(@RequestParam String document) {
        try {
            log.info("Finding user by document: {}", document);
            var user = findUserPortIn.findByDocument(document);
            log.info("Found user: {}", user);
            return ResponseEntity.ok(user);
        } catch (Exception ex) {
            LOGGER.error("Error finding user by document {}: {}", document, ex.getMessage());
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

}