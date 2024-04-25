package io.bookwise.application.core.ports.out;

import io.bookwise.application.core.domain.Student;

import java.util.Optional;

public interface FindStudentPortOut {
    Optional<Student> findByDocument(String document);
}