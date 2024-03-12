package ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.users;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GeoDTO {
    @NotEmpty(message = "Latitude must not be empty")
    private String lat;

    @NotEmpty(message = "Longitude must not be empty")
    private String lng;
}
