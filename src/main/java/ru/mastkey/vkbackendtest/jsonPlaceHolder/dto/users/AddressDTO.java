package ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.users;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressDTO {
    @NotEmpty(message = "Street must not be empty")
    private String street;

    @NotEmpty(message = "Suite must not be empty")
    private String suite;

    @NotEmpty(message = "City must not be empty")
    private String city;

    @NotEmpty(message = "Zipcode must not be empty")
    private String zipcode;

    @NotNull(message = "Geo must not be null")
    @Valid
    private GeoDTO geo;
}