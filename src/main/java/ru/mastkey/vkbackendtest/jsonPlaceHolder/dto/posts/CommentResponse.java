package ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.posts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private Integer postId;
    private Integer id;
    private String name;
    private String email;
    private String body;
}
