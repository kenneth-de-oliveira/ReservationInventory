package io.bookwise.adapters.out.mapper;

import io.bookwise.adapters.out.repository.entity.StudentEntity;
import io.bookwise.application.core.domain.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface StudentMapper {

    @Mapping(source = "address", target = "address")
    Student toDomain(StudentEntity studentEntity);

    @Mapping(source = "address", target = "address")
    StudentEntity toEntity(Student student);

}