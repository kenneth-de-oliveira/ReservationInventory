package io.bookwise.adapters.out;

import io.bookwise.adapters.out.client.InventoryManagementClient;
import io.bookwise.adapters.out.mapper.InventoryManagementMapper;
import io.bookwise.adapters.out.mapper.ReservationInventoryMapper;
import io.bookwise.adapters.out.repository.ReservationControlRepository;
import io.bookwise.adapters.out.repository.ReservationInventoryRepository;
import io.bookwise.adapters.out.repository.dto.ReserveInfo;
import io.bookwise.adapters.out.repository.entity.ReservationEntity;
import io.bookwise.adapters.out.repository.enums.ReservationControlStatus;
import io.bookwise.application.core.domain.Reservation;
import io.bookwise.application.core.ports.out.ReservationInventoryPortOut;
import io.bookwise.framework.errors.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.bookwise.adapters.out.repository.enums.ReservationControlStatus.CONFIRMED;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationInventoryAdapterOut implements ReservationInventoryPortOut {

    private final ReservationInventoryRepository repository;
    private final ReservationControlRepository reservationControlRepository;
    private final InventoryManagementClient inventoryManagementClient;
    private final InventoryManagementMapper inventoryManagementMapper;
    private final ReservationInventoryMapper mapper;

    @Override
    public void execute(Reservation reservation) {
        log.info("Creating reservation for book with ISBN: {} for student with document: {}", reservation.getIsbn(), reservation.getDocument());
        this.reserve(reservation);
        log.info("Reservation created successfully with status {}", CONFIRMED);
    }

    private void reserve(Reservation reservation) {
        Stream.ofNullable(reservation)
                .map(mapper::toEntity)
                .map(repository::save)
                .peek(reservationEntity -> this.updateReservationControl(reservation))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Failed to create reservation for book with ISBN: " + reservation.getIsbn()));
    }

    private void updateReservationControl(Reservation reservation) {
        Stream.ofNullable(reservation)
                .filter(isbnValue -> Objects.nonNull(reservation.getDocument()))
                .peek(reservationValue -> {
                    reservationControlRepository.findByIsbnAndStatus(reservationValue.getIsbn(), ReservationControlStatus.PENDING)
                            .ifPresent(existingControl -> {
                                existingControl.setStatus(ReservationControlStatus.CONFIRMED);
                                existingControl.setUpdatedAt(LocalDateTime.now());
                                reservationControlRepository.save(existingControl);
                            });
                })
                .findFirst()
                .orElseThrow();
    }

    @Override
    public List<ReserveInfo> findAllByDocument(String document) {
        var response = inventoryManagementClient.findAll();
        var books = inventoryManagementMapper.toBookDomainList(response);

        var reservationIsbns = repository.findByDocument(document).stream()
                .map(ReservationEntity::getIsbn)
                .collect(Collectors.toSet());

        var reservations = books.parallelStream()
                .filter(book -> reservationIsbns.contains(book.getIsbn()))
                .map(book -> new ReserveInfo(book.getTitle(), book.getAuthorName(), book.getIsbn()))
                .toList();

        log.info("Reservations found: {}", reservations.size());
        return reservations;
    }

    public void deleteByIsbnAndDocument(String isbn, String document) {
        log.info("Deleting reservation for ISBN: {} and student: {}", isbn, document);
        repository.deleteByIsbnAndDocument(isbn, document);
        log.info("Reservation deleted successfully for ISBN: {} and student: {}", isbn, document);
    }

    @Override
    public Boolean checkIfBookIsReservedByIsbn(String isbn) {
        var isReserved = repository.existsByIsbn(isbn);
        log.info("Book with ISBN: {} is reserved: {}", isbn, isReserved);
        return isReserved;
    }

    @Override
    public Boolean checkIfBookIsReservedByIsbnAndDocument(String isbn, String document) {
        var isReserved = repository.existsByIsbnAndDocument(isbn, document);
        log.info("Book with ISBN: {} is reserved by document {}: {}", isbn, document, isReserved);
        return isReserved;
    }
}