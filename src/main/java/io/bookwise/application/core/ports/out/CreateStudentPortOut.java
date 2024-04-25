package io.bookwise.application.core.ports.out;

import io.bookwise.application.core.domain.Student;

public interface CreateStudentPortOut {
    Student create(Student student);
}