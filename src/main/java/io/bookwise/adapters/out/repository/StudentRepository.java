package io.bookwise.adapters.out.repository;

import io.bookwise.adapters.out.repository.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    Optional<StudentEntity> findByDocument(String document);

}