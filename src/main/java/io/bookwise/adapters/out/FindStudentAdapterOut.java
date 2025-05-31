package io.bookwise.adapters.out;

import io.bookwise.adapters.out.mapper.StudentMapper;
import io.bookwise.adapters.out.repository.StudentRepository;
import io.bookwise.application.core.domain.Student;
import io.bookwise.application.core.ports.out.FindStudentPortOut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class FindStudentAdapterOut implements FindStudentPortOut {

    private final StudentRepository repository;
    private final StudentMapper mapper;

    @Override
    public Optional<Student> findByDocument(String document) {
        log.info("Finding student by document: {}", document);
        Optional<Student> student = repository.findByDocument(document).map(mapper::toDomain);
        log.info("Student found: {}", student);
        return student;
    }

}