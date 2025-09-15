package io.bookwise.adapters.in;

import io.bookwise.application.core.domain.User;
import io.bookwise.application.core.ports.in.CreateUserPortIn;
import io.bookwise.application.core.ports.in.FindUserPortIn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}