package io.bookwise.adapters.out.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@Data
@Builder
@Entity(name = "student")
@AllArgsConstructor
@NoArgsConstructor
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @CPF
    private String document;

    @NotNull
    private String name;

    @Email
    @NotNull
    private String email;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private AddressEntity address;

}