package io.bookwise.adapters.out.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.ISBN;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Data
@Builder
@Entity(name = "book")
@AllArgsConstructor
@NoArgsConstructor
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String authorName;

    @NotNull
    private String text;

    @ISBN
    @NotNull
    private String isbn;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private CategoryEntity category;

    private Boolean reserved;

    @PrePersist
    public void prePersist() {
        if (Objects.isNull(reserved)) {
            reserved = Boolean.FALSE;
        }
    }

}