package io.bookwise.adapters.out;

import io.bookwise.adapters.out.mapper.StudentMapper;
import io.bookwise.adapters.out.repository.StudentRepository;
import io.bookwise.application.core.domain.Student;
import io.bookwise.application.core.ports.out.CreateStudentPortOut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateStudentAdapterOut implements CreateStudentPortOut {

    private final StudentRepository repository;
    private final StudentMapper mapper;

    @Override
    public Student create(Student student) {
        log.info("Creating student: {}", student);
        var studentEntity = mapper.toEntity(student);
        var entity = repository.save(studentEntity);
        log.info("Student created: {}", entity);
        return mapper.toDomain(entity);
    }

}