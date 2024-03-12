package ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.albums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotosResponse {
    private Integer albumId;
    private Integer id;
    private String title;
    private String url;
    private String thumbnailUrl;
}
