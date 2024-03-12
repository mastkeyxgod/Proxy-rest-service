package ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToDosResponse {
    private Integer userId;
    private Integer id;
    private String title;
    private Boolean completed;
}
