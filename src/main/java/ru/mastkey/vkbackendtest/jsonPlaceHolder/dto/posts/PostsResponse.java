package ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.posts;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostsResponse {
    private Integer userId;
    private Integer id;
    private String title;
    private String body;
}
