package io.bookwise.application.usecase;

import io.bookwise.adapters.out.mail.MailMessage;
import io.bookwise.application.core.domain.Student;
import io.bookwise.application.core.ports.in.CreateStudentPortIn;
import io.bookwise.application.core.ports.out.CreateStudentPortOut;
import io.bookwise.application.core.ports.out.FindAddressByPostalCodePortOut;
import io.bookwise.application.core.ports.out.SmtpMailMessagePortOut;

public class CreateStudentUseCase implements CreateStudentPortIn {

    private final CreateStudentPortOut createStudentPortOut;
    private final FindAddressByPostalCodePortOut findAddressByPostalCodePortOut;
    private final SmtpMailMessagePortOut smtpMailMessagePortOut;

    public CreateStudentUseCase(
            CreateStudentPortOut createStudentPortOut,
            FindAddressByPostalCodePortOut findAddressByPostalCodePortOut,
            SmtpMailMessagePortOut smtpMailMessagePortOut) {
        this.createStudentPortOut = createStudentPortOut;
        this.findAddressByPostalCodePortOut = findAddressByPostalCodePortOut;
        this.smtpMailMessagePortOut = smtpMailMessagePortOut;
    }

    @Override
    public Student create(Student student) {
        this.findAddressByPostalCode(student);
        student = createStudentPortOut.create(student);
        var mail = MailMessage.builder()
                .to(student.getEmail())
                .subject("ReservationInventory - Email Confirmation")
                .text("Your email has been confirmed successfully!")
                .build();
        smtpMailMessagePortOut.sendMail(mail);
        return student;
    }

    private void findAddressByPostalCode(Student student) {
        var address = findAddressByPostalCodePortOut.find(student.getAddress().getPostalCode());
        student.setAddress(address);
    }

}