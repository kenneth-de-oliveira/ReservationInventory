package io.bookwise.adapters.out;

import io.bookwise.adapters.out.repository.dto.CancelReservationRequest;
import io.bookwise.application.core.ports.out.CancelReservationPortOut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CancelReservationAdapterOut implements CancelReservationPortOut {

    @Override
    public void cancel(CancelReservationRequest reserve) {
        throw new UnsupportedOperationException("This adapter is not implemented yet");
    }

}