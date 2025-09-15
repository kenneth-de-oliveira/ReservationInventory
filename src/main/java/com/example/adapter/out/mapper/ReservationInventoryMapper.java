package com.example.adapter.out.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.adapter.out.pub.dto.ReservationRequest;
import com.example.adapter.out.repository.entity.ReservationEntity;
import com.example.application.core.domain.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationInventoryMapper {

    Reservation toDomain(ReservationEntity reservationEntity);

    Reservation toDomain(ReservationRequest reservationRequest);

    ReservationEntity toEntity(Reservation reservation);

    ReservationEntity toEntity(ReservationRequest reservationRequest);

    @Mapping(target = "isbn", source = "isbn")
    @Mapping(target = "document", source = "document")
    Reservation toDomain(String isbn, String document);

    @Mapping(target = "isbn", source = "isbn")
    @Mapping(target = "document", source = "document")
    ReservationEntity toEntity(String isbn, String document);

    @Mapping(target = "isbn", source = "isbn")
    @Mapping(target = "document", source = "document")
    ReservationRequest toRequest(String isbn, String document);

    default Reservation toDomain(String payload) {
        try {
            var mapper = new ObjectMapper();
            return mapper.readValue(payload, Reservation.class);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }
}