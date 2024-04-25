package io.bookwise.application.usecase;

import io.bookwise.application.core.domain.Student;
import io.bookwise.application.core.ports.in.CreateStudentPortIn;
import io.bookwise.application.core.ports.out.CreateStudentPortOut;
import io.bookwise.application.core.ports.out.FindAddressByPostalCodePortOut;
import io.bookwise.application.core.ports.out.SendMailMessagePortOut;

public class CreateStudentUseCase implements CreateStudentPortIn {

    private final CreateStudentPortOut createStudentPortOut;
    private final FindAddressByPostalCodePortOut findAddressByPostalCodePortOut;
    private final SendMailMessagePortOut sendMailMessagePortOut;

    public CreateStudentUseCase(
            CreateStudentPortOut createStudentPortOut,
            FindAddressByPostalCodePortOut findAddressByPostalCodePortOut,
            SendMailMessagePortOut sendMailMessagePortOut) {
        this.createStudentPortOut = createStudentPortOut;
        this.findAddressByPostalCodePortOut = findAddressByPostalCodePortOut;
        this.sendMailMessagePortOut = sendMailMessagePortOut;
    }

    @Override
    public Student create(Student student) {
        this.findAddressByPostalCode(student);
        student = this.createStudentAndNotify(student);
        return student;
    }

    private Student createStudentAndNotify(Student student) {
        student = createStudentPortOut.create(student);
        sendMailMessagePortOut.send(student.getEmail());
        return student;
    }

    private void findAddressByPostalCode(Student student) {
        var address = findAddressByPostalCodePortOut.find(student.getAddress().getPostalCode());
        student.setAddress(address);
    }

}