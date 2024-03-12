package ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.users;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsersRequest {
    @NotEmpty(message = "Name must not be empty")
    private String name;

    @NotEmpty(message = "Username must not be empty")
    private String username;

    @NotEmpty(message = "Email must not be empty")
    private String email;

    @NotNull(message = "Address must not be null")
    @Valid
    private AddressDTO address;

    @NotEmpty(message = "Phone must not be empty")
    private String phone;

    @NotEmpty(message = "Website must not be empty")
    private String website;

    @NotNull(message = "Company must not be null")
    @Valid
    private CompanyDTO company;
}
