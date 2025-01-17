package io.bookwise.adapters.out.repository;

import io.bookwise.adapters.out.repository.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    Optional<BookEntity> findByIsbn(String isbn);
    @Query("SELECT b FROM book b WHERE b.category.name = :category")
    List<BookEntity> findAllByCategory(String category);
}