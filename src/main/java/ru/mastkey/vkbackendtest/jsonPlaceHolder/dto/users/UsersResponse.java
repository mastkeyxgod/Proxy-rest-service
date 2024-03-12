package ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.users;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsersResponse {
    private Integer id;
    private String name;
    private String username;
    private String email;
    private AddressDTO address;
    private String phone;
    private String website;
    private CompanyDTO company;
}