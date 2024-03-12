package ru.mastkey.vkbackendtest.jsonPlaceHolder.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.albums.AlbumsRequest;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.albums.AlbumsResponse;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.albums.PhotosResponse;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.service.AlbumsService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jph")
@RequiredArgsConstructor
@Tag(description = "Контроллер для перенаправления запросов на https://jsonplaceholder.typicode.com/albums/**", name = "Albums Controller")
public class AlbumsController {

    private final AlbumsService albumsService;


    @Operation(
            summary = "Получение всех альбомов(Authorization: READ_ALBUMS_PRIVILEGE)",
            description = "Позволяет получить список всех альбомов"
    )
    @GetMapping("/albums")
    public ResponseEntity<List<AlbumsResponse>> getAllAlbums() {
        return albumsService.getAllAlbums();
    }


    @Operation(
            summary = "Получение альбома(Authorization: READ_ALBUMS_PRIVILEGE)",
            description = "Позволяет получить конкретный альбом по его id"
    )
    @GetMapping("/albums/{id}")
    public ResponseEntity<AlbumsResponse> getAlbumById(@PathVariable @Parameter(description = "Id альбома") Long id) {
        return albumsService.getAlbumById(id);
    }


    @Operation(
            summary = "Получение всех фотографий с альбома(Authorization: READ_ALBUMS_PRIVILEGE)",
            description = "Позволяет получить список всех фотографий с конкретного альбома по его id"
    )
    @GetMapping("/albums/{id}/photos")
    public ResponseEntity<List<PhotosResponse>> getPhotosByAlbumId(@PathVariable @Parameter(description = "Id альбома") Long id) {
        return albumsService.getPhotosByAlbumId(id);
    }


    @Operation(
            summary = "Создание альбома(Authorization: CREATE_ALBUMS_PRIVILEGE)",
            description = "Позволяет создать новый альбом"
    )
    @PostMapping("/albums")
    public ResponseEntity<AlbumsResponse> createAlbum(@Valid @RequestBody AlbumsRequest albumsRequest) {
        return albumsService.createAlbum(albumsRequest);
    }


    @Operation(
            summary = "Обновление альбома(Authorization: UPDATE_ALBUMS_PRIVILEGE)",
            description = "Позволяет полность обновить альбом"
    )
    @PutMapping("/albums/{id}")
    public ResponseEntity<AlbumsResponse> updateAlbum(@PathVariable @Parameter(description = "Id альбома") Long id, @Valid @RequestBody AlbumsRequest albumsRequest) {
        return albumsService.updateAlbum(id, albumsRequest);
    }


    @Operation(
            summary = "Обновление альбома(Authorization: UPDATE_ALBUMS_PRIVILEGE)",
            description = "Позволяет обновить конкретные поля в альбоме"
    )
    @PatchMapping("/albums/{id}")
    public ResponseEntity<AlbumsResponse> patchAlbumById(@PathVariable @Parameter(description = "Id альбома") Long id, @RequestBody AlbumsRequest albumsRequest) {
        return albumsService.patchAlbumById(id, albumsRequest);
    }

    @Operation(
            summary = "Удаление альбома(Authorization: DELETE_ALBUMS_PRIVILEGE)",
            description = "Позволяет удалить конкретный альбом по его id"
    )
    @DeleteMapping("/albums/{id}")
    public ResponseEntity<?> deleteAlbumById(@PathVariable @Parameter(description = "Id альбома") Long id) {
        return albumsService.deleteAlbumById(id);
    }
}
