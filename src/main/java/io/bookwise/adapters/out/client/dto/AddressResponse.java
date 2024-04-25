package io.bookwise.adapters.out.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AddressResponse(@JsonProperty("logradouro") String street,
                              @JsonProperty("localidade") String city,
                              @JsonProperty("uf") String state,
                              @JsonProperty("cep") String postalCode) {

}