package io.bookwise.adapters.out;

import io.bookwise.adapters.out.repository.StudentRepository;
import io.bookwise.application.core.domain.Student;
import io.bookwise.application.core.ports.out.CreateStudentPortOut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static io.bookwise.adapters.out.mapper.StudentMapper.toDomain;
import static io.bookwise.adapters.out.mapper.StudentMapper.toEntity;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateStudentAdapterOut implements CreateStudentPortOut {

    private final StudentRepository repository;

    @Override
    public Student create(Student student) {
        log.info("Creating student: {}", student);
        var studentEntity = toEntity(student);
        var entity = repository.save(studentEntity);
        log.info("Student created: {}", entity);
        return toDomain(entity);
    }

}