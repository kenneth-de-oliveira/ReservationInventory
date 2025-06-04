package io.bookwise.adapters.out.repository;

import io.bookwise.adapters.out.repository.entity.ReservationEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationInventoryRepository extends JpaRepository<ReservationEntity, Long> {
    @Query(nativeQuery = true, value = "select b.title, b.author_name as authorName, b.isbn from reservation r join book b on b.isbn = r.isbn where r.document = :document")
    <T> List<T> find(String document, Class<T> type);

    Boolean existsByIsbn(String isbn);

    List<ReservationEntity> findByDocument(String document);

    Boolean existsByIsbnAndDocument(String isbn, String document);

    @Transactional
    void deleteByIsbnAndDocument(String isbn, String document);
}