package ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.users;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CompanyDTO {
    @NotEmpty(message = "Name must not be empty")
    private String name;

    @NotEmpty(message = "Catch phrase must not be empty")
    private String catchPhrase;

    @NotEmpty(message = "Business strategy must not be empty")
    private String bs;
}