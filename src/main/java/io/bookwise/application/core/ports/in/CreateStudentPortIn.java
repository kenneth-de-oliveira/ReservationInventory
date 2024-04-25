package io.bookwise.application.core.ports.in;

import io.bookwise.application.core.domain.Student;

public interface CreateStudentPortIn {
    Student create(Student student);
}