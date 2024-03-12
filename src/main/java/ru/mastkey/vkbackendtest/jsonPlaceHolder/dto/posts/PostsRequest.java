package ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.posts;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostsRequest {
    @NotNull(message = "User ID must not be null")
    private Integer userId;

    @NotNull(message = "Title must not be null")
    @Size(min = 1, message = "Title must not be empty")
    private String title;

    @NotNull(message = "Body must not be null")
    @Size(min = 1, message = "Body must not be empty")
    private String body;
}
