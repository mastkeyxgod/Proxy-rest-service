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

    public List<AlbumsResponse> getAllAlbums() {
        List<AlbumsResponse> albums = albumsClient.getAllAlbums();
        return albums;
    }

    @Cacheable
    public AlbumsResponse getAlbumById(Long id) {
        AlbumsResponse album = albumsClient.getAlbumById(id);
        return album;
    }

    public List<PhotosResponse> getPhotosByAlbumId(Long id) {
        List<PhotosResponse> photos = albumsClient.getPhotosByAlbumId(id);
        return photos;
    }

    @CacheEvict
    public void deleteAlbumById(Long id) {
        albumsClient.deleteAlbumById(id);

    }

    public AlbumsResponse createAlbum(AlbumsRequest albumsRequest) {
        AlbumsResponse albumsResponse = albumsClient.createAlbum(albumsRequest);
        return albumsResponse;
    }

    @CachePut
    public AlbumsResponse updateAlbum(Long id, AlbumsRequest albumsRequest) {
        AlbumsResponse albumsResponse = albumsClient.updateAlbum(id, albumsRequest);
        return albumsResponse;
    }

    @CachePut
    public AlbumsResponse patchAlbumById(Long id, AlbumsRequest albumsRequest) {
        AlbumsResponse albumsResponse = albumsClient.patchAlbumById(id, albumsRequest);
        return albumsResponse;
    }
}
