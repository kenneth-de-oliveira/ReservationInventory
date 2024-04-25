package io.bookwise.application.usecase;

import io.bookwise.application.core.domain.Student;
import io.bookwise.application.core.ports.in.FindStudentPortIn;
import io.bookwise.application.core.ports.out.FindStudentPortOut;

public class FindStudentUseCase implements FindStudentPortIn {

    private final FindStudentPortOut findStudentPortOut;

    public FindStudentUseCase(FindStudentPortOut findStudentPortOut) {
        this.findStudentPortOut = findStudentPortOut;
    }

    @Override
    public Student findByDocument(String document) {
        return findStudentPortOut.findByDocument(document).orElseThrow(() -> new RuntimeException("Student not Found"));
    }

}