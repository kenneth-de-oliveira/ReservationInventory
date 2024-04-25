package io.bookwise.adapters.out.mapper;

import io.bookwise.adapters.out.repository.entity.StudentEntity;
import io.bookwise.application.core.domain.Student;

public class StudentMapper {

    public static Student toDomain(StudentEntity studentEntity) {
        var student = new Student();
        student.setId(studentEntity.getId());
        student.setDocument(studentEntity.getDocument());
        student.setEmail(studentEntity.getEmail());
        student.setName(studentEntity.getName());
        student.setAddress(AddressMapper.toDomain(studentEntity.getAddress()));
        return student;
    }

    public static StudentEntity toEntity(Student student) {
        var studentEntity = new StudentEntity();
        studentEntity.setDocument(student.getDocument());
        studentEntity.setEmail(student.getEmail());
        studentEntity.setName(student.getName());
        studentEntity.setAddress(AddressMapper.toEntity(student.getAddress()));
        return studentEntity;
    }

}