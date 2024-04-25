package io.bookwise.adapters.out.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.bookwise.adapters.out.pub.dto.ReservationRequest;
import io.bookwise.adapters.out.repository.entity.ReservationEntity;
import io.bookwise.application.core.domain.Reservation;

public class ReservationInventoryMapper {

    public static Reservation toDomain(ReservationEntity reservationEntity) {
        var reservation = new Reservation();
        reservation.setIsbn(reservationEntity.getIsbn());
        reservation.setDocument(reservationEntity.getDocument());
        return reservation;
    }

    public static Reservation toDomain(ReservationRequest reservationRequest) {
        var reservation = new Reservation();
        reservation.setIsbn(reservationRequest.getIsbn());
        reservation.setDocument(reservationRequest.getDocument());
        return reservation;
    }

    public static Reservation toDomain(String payload) {
        try {
            var mapper = new ObjectMapper();
            return mapper.readValue(payload, Reservation.class);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    public static ReservationEntity toEntity(Reservation reservation) {
        var reservationEntity = new ReservationEntity();
        reservationEntity.setIsbn(reservation.getIsbn());
        reservationEntity.setDocument(reservation.getDocument());
        return reservationEntity;
    }

    public static ReservationEntity toEntity(String isbn, String document) {
        var reservationEntity = new ReservationEntity();
        reservationEntity.setIsbn(isbn);
        reservationEntity.setDocument(document);
        return reservationEntity;
    }

    public static ReservationEntity toEntity(ReservationRequest reservationRequest) {
        var reservationEntity = new ReservationEntity();
        reservationEntity.setIsbn(reservationRequest.getIsbn());
        reservationEntity.setDocument(reservationRequest.getDocument());
        return reservationEntity;
    }

    public static ReservationRequest toRequest(String isbn, String document) {
        var reservationRequest = new ReservationRequest();
        reservationRequest.setIsbn(isbn);
        reservationRequest.setDocument(document);
        return reservationRequest;
    }

}