package ru.mastkey.vkbackendtest.jsonPlaceHolder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.client.AlbumsClient;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.albums.AlbumsRequest;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.albums.AlbumsResponse;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.albums.PhotosResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "AlbumsCache")
public class AlbumsService {

    private final AlbumsClient albumsClient;

    public ResponseEntity<List<AlbumsResponse>> getAllAlbums() {
        List<AlbumsResponse> albums = albumsClient.getAllAlbums();
        return ResponseEntity.ok(albums);
    }

    @Cacheable
    public ResponseEntity<AlbumsResponse> getAlbumById(Long id) {
        AlbumsResponse album = albumsClient.getAlbumById(id);
        return ResponseEntity.ok(album);
    }

    public ResponseEntity<List<PhotosResponse>> getPhotosByAlbumId(Long id) {
        List<PhotosResponse> photos = albumsClient.getPhotosByAlbumId(id);
        return ResponseEntity.ok(photos);
    }

    @CacheEvict
    public ResponseEntity<?> deleteAlbumById(Long id) {
        albumsClient.deleteAlbumById(id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<AlbumsResponse> createAlbum(AlbumsRequest albumsRequest) {
        AlbumsResponse albumsResponse = albumsClient.createAlbum(albumsRequest);
        return ResponseEntity.ok(albumsResponse);
    }

    @CachePut
    public ResponseEntity<AlbumsResponse> updateAlbum(Long id, AlbumsRequest albumsRequest) {
        AlbumsResponse albumsResponse = albumsClient.updateAlbum(id, albumsRequest);
        return ResponseEntity.ok(albumsResponse);
    }

    @CachePut
    public ResponseEntity<AlbumsResponse> patchAlbumById(Long id, AlbumsRequest albumsRequest) {
        AlbumsResponse albumsResponse = albumsClient.patchAlbumById(id, albumsRequest);
        return ResponseEntity.ok(albumsResponse);
    }
}
