package io.bookwise.adapters.out.client;

import feign.Param;
import feign.RequestLine;
import io.bookwise.adapters.out.client.dto.AddressResponse;

public interface FindAddressByPostalCodeClient {

    @RequestLine("GET /{postalCode}/json")
    AddressResponse find(@Param("postalCode") String postalCode);

}