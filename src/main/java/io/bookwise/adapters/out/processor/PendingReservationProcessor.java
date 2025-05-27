package io.bookwise.adapters.out.processor;

import io.bookwise.adapters.out.ReservationInventoryAdapterOut;
import io.bookwise.adapters.out.repository.entity.ReservationControlEntity;
import io.bookwise.adapters.out.repository.enums.ReservationControlStatus;
import io.bookwise.application.core.domain.Reservation;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Lazy))
public final class PendingReservationProcessor extends ReservationProcessor {

    private ReservationInventoryAdapterOut reservationInventoryAdapterOut;

    @Override
    public boolean supportsStatus(ReservationControlStatus reservationControlStatus) {
        return ReservationControlStatus.PENDING.equals( reservationControlStatus );
    }

    @Override
    public void process(Reservation reservation, ReservationControlEntity reservationControlEntity) {
        reservationInventoryAdapterOut.reserve(reservation, reservationControlEntity);
    }
}