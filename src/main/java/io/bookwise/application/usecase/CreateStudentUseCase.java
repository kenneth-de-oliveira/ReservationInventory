package io.bookwise.application.usecase;

import io.bookwise.application.core.dto.Email;
import io.bookwise.application.core.domain.Student;
import io.bookwise.application.core.ports.in.CreateStudentPortIn;
import io.bookwise.application.core.ports.out.CreateStudentPortOut;
import io.bookwise.application.core.ports.out.FindAddressByPostalCodePortOut;
import io.bookwise.application.core.ports.out.EmailServicePortOut;

public class CreateStudentUseCase implements CreateStudentPortIn {

    private final CreateStudentPortOut createStudentPortOut;
    private final FindAddressByPostalCodePortOut findAddressByPostalCodePortOut;
    private final EmailServicePortOut emailServicePortOut;

    public CreateStudentUseCase(
            CreateStudentPortOut createStudentPortOut,
            FindAddressByPostalCodePortOut findAddressByPostalCodePortOut,
            EmailServicePortOut emailServicePortOut) {
        this.createStudentPortOut = createStudentPortOut;
        this.findAddressByPostalCodePortOut = findAddressByPostalCodePortOut;
        this.emailServicePortOut = emailServicePortOut;
    }

    @Override
    public Student create(Student student) {
        this.findAddressByPostalCode(student);
        student = createStudentPortOut.create(student);
        var email = Email.builder()
                .to(student.getEmail())
                .subject("ReservationInventory - Email Confirmation")
                .text("Your email has been confirmed successfully!")
                .build();
        emailServicePortOut.send(email);
        return student;
    }

    private void findAddressByPostalCode(Student student) {
        var address = findAddressByPostalCodePortOut.find(student.getAddress().getPostalCode());
        student.setAddress(address);
    }

}