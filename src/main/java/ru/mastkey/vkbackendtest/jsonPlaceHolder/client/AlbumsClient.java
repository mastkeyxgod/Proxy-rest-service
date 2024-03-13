package ru.mastkey.vkbackendtest.jsonPlaceHolder.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.albums.AlbumsRequest;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.albums.AlbumsResponse;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.albums.PhotosResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AlbumsClient {
    private final ObjectMapper objectMapper;

    private final CloseableHttpClient closeableHttpClient;

    @Value("${albums.url}")
    private String url;

    public List<AlbumsResponse> getAllAlbums() {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-Type", "application/json");

        try (CloseableHttpResponse response = closeableHttpClient.execute(httpGet)) {
            return objectMapper.readValue(response.getEntity().getContent(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, AlbumsResponse.class));
        } catch (IOException e) {
            throw new RuntimeException("Failed to get data from endpoint " + url, e);
        }
    }

    public AlbumsResponse getAlbumById(Long id) {
        log.info("Get album by id {}", id);
        String url = this.url + "/" + id;
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-Type", "application/json");

        try (CloseableHttpResponse response = closeableHttpClient.execute(httpGet)) {
            return objectMapper.readValue(response.getEntity().getContent(), AlbumsResponse.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to get data from endpoint " + url, e);
        }
    }

    public List<PhotosResponse> getPhotosByAlbumId(Long id) {
        String url = this.url + "/" + id + "/photos";
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-Type", "application/json");

        try (CloseableHttpResponse response = closeableHttpClient.execute(httpGet)) {
            return objectMapper.readValue(response.getEntity().getContent(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, PhotosResponse.class));
        } catch (IOException e) {
            throw new RuntimeException("Failed to get data from endpoint " + url, e);
        }
    }

    public AlbumsResponse createAlbum(AlbumsRequest albumsRequest) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");

        StringEntity entity;
        try {
            entity = new StringEntity(objectMapper.writeValueAsString(albumsRequest));
        } catch (UnsupportedEncodingException | JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize data", e);
        }
        httpPost.setEntity(entity);
        try (CloseableHttpResponse response = closeableHttpClient.execute(httpPost)) {
            return objectMapper.readValue(response.getEntity().getContent(), AlbumsResponse.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to post data to endpoint " + url, e);
        }
    }

    public void deleteAlbumById(Long id) {
        String url = this.url + "/" + id;
        HttpDelete httpDelete = new HttpDelete(url);
        httpDelete.setHeader("Content-Type", "application/json");

        try (CloseableHttpResponse response = closeableHttpClient.execute(httpDelete)) {
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed to delete album");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete data to endpoint " + url, e);
        }
    }

    public AlbumsResponse updateAlbum(Long id, AlbumsRequest albumsRequest) {
        String url = this.url + "/" + id;
        HttpPut httpPut = new HttpPut(url);
        httpPut.setHeader("Content-Type", "application/json");

        StringEntity entity;
        try {
            entity = new StringEntity(objectMapper.writeValueAsString(albumsRequest));
        } catch (UnsupportedEncodingException | JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize data", e);
        }

        httpPut.setEntity(entity);
        try (CloseableHttpResponse response = closeableHttpClient.execute(httpPut)) {
            return objectMapper.readValue(response.getEntity().getContent(), AlbumsResponse.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to put data to endpoint " + url, e);
        }
    }

    public AlbumsResponse patchAlbumById(Long id, AlbumsRequest albumsRequest) {
        String url = this.url + "/" + id;
        HttpPatch httpPatch = new HttpPatch(url);
        httpPatch.setHeader("Content-Type", "application/json");

        StringEntity entity;
        try {
            entity = new StringEntity(objectMapper.writeValueAsString(albumsRequest));
        } catch (UnsupportedEncodingException | JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize data", e);
        }

        httpPatch.setEntity(entity);
        try (CloseableHttpResponse response = closeableHttpClient.execute(httpPatch)) {
            return objectMapper.readValue(response.getEntity().getContent(), AlbumsResponse.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to patch data to endpoint " + url, e);
        }
    }
}
