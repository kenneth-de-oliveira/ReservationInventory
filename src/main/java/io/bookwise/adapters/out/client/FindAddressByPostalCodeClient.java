package io.bookwise.adapters.out.client;

import io.bookwise.adapters.out.client.dto.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "FindAddressByPostalCodeClient",
        url = "${api.viacep.url}"
)
public interface FindAddressByPostalCodeClient {

    @GetMapping("/{postalCode}/json")
    AddressResponse find(@PathVariable String postalCode);

}