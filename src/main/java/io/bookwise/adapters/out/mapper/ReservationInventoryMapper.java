package io.bookwise.adapters.out.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.bookwise.adapters.out.pub.dto.ReservationRequest;
import io.bookwise.adapters.out.repository.entity.ReservationEntity;
import io.bookwise.application.core.domain.Reservation;
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