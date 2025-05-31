package io.bookwise.adapters.out;

import io.bookwise.adapters.out.client.InventoryManagementClient;
import io.bookwise.adapters.out.mapper.InventoryManagementMapper;
import io.bookwise.adapters.out.mapper.ReservationInventoryMapper;
import io.bookwise.adapters.out.processor.ReservationProcessor;
import io.bookwise.adapters.out.repository.ReservationControlRepository;
import io.bookwise.adapters.out.repository.ReservationInventoryRepository;
import io.bookwise.adapters.out.repository.dto.ReserveInfo;
import io.bookwise.adapters.out.repository.entity.ReservationControlEntity;
import io.bookwise.adapters.out.repository.entity.ReservationEntity;
import io.bookwise.adapters.out.repository.enums.ReservationControlStatus;
import io.bookwise.application.core.domain.Reservation;
import io.bookwise.application.core.ports.out.ReservationInventoryPortOut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static io.bookwise.adapters.out.repository.enums.ReservationControlStatus.CONFIRMED;
import static io.bookwise.adapters.out.repository.enums.ReservationControlStatus.PENDING;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationInventoryAdapterOut implements ReservationInventoryPortOut {

    private final ReservationInventoryRepository repository;
    private final ReservationControlRepository reservationControlRepository;
    private final InventoryManagementClient inventoryManagementClient;
    private final InventoryManagementMapper inventoryManagementMapper;
    private final List<ReservationProcessor> reservationProcessors;
    private final ReservationInventoryMapper mapper;

    @Override
    public void execute(Reservation reservation) {
        log.info("Creating reservation for book with ISBN: {} for student with document: {}", reservation.getIsbn(), reservation.getDocument());
        var reservationControlEntity = reservationControlRepository.findByIsbnAndStatus(reservation.getIsbn(), PENDING);
        reservationControlEntity.ifPresent(control -> this.processReservation(reservation, control));
        log.info("Reservation created successfully with status {}", CONFIRMED);
    }

    private void processReservation(Reservation reservation, ReservationControlEntity reservationControlEntity) {
        reservationProcessors.stream()
                .filter(reservationProcessor -> reservationProcessor.supportsStatus(reservationControlEntity.getStatus()))
                .findFirst()
                .ifPresentOrElse(
                        reservationProcessor -> reservationProcessor.process(reservation, reservationControlEntity),
                        () -> this.updateReservationStatus(reservationControlEntity, ReservationControlStatus.ERROR)
                );
    }

    public void reserve(Reservation reservation, ReservationControlEntity reservationControlEntity) {
        repository.save( mapper.toEntity(reservation) );
        this.updateReservationStatus(reservationControlEntity, CONFIRMED);
    }

    private void updateReservationStatus(ReservationControlEntity reservationControlEntity, ReservationControlStatus reservationControlStatus) {
        reservationControlEntity.setStatus(reservationControlStatus);
        reservationControlRepository.save(reservationControlEntity);
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

    @Override
    public Boolean checkIfBookIsReservedByIsbn(String isbn) {
        var isReserved = repository.existsByIsbn(isbn);
        log.info("Book with ISBN: {} is reserved: {}", isbn, isReserved);
        return isReserved;
    }

}