package io.bookwise.adapters.in;

import io.bookwise.application.core.domain.Student;
import io.bookwise.application.core.ports.in.CreateStudentPortIn;
import io.bookwise.application.core.ports.in.FindStudentPortIn;
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
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentResourceAdapterIn {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentResourceAdapterIn.class);

    private final CreateStudentPortIn createStudentPortIn;
    private final FindStudentPortIn findStudentPortIn;

    @PostMapping
    public ResponseEntity<Student> create(@RequestBody Student student) {
        try {
            log.info("Creating student with students controller : {}", student);
            student = createStudentPortIn.create(student);
            ResponseEntity<Student> responseEntity = ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{document}")
                    .buildAndExpand(student.getDocument()).toUri()).body(student);
            log.info("Created student with students controller : {}", student);
            return responseEntity;

        }catch (Exception ex){
            LOGGER.error("Error creating Student: {}", ex.getMessage());
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

}