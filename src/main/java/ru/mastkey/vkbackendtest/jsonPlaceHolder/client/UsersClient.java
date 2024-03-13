package ru.mastkey.vkbackendtest.jsonPlaceHolder.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.albums.AlbumsResponse;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.posts.PostsResponse;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.users.ToDosResponse;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.users.UsersRequest;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.users.UsersResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Component
@Slf4j
public class UsersClient {
    private final CloseableHttpClient httpClient;
    private final ObjectMapper objectMapper;

    @Value("${users.url}")
    private String url;

    public UsersClient(CloseableHttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    public List<UsersResponse> getAllUsers() {
        HttpGet httpGet = new HttpGet(url);

        try (CloseableHttpResponse response = httpClient.execute(httpGet)){
            return objectMapper.readValue(response.getEntity().getContent(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, UsersResponse.class));
        } catch (IOException e) {
            throw new RuntimeException("Failed to get data from endpoint " + url, e);
        }
    }

    public UsersResponse getUserById(Long id) {
        log.info("Getting user with id {}", id);
        String url = this.url + "/" + id;
        HttpGet httpGet = new HttpGet(url);

        try (CloseableHttpResponse response = httpClient.execute(httpGet)){
            return objectMapper.readValue(response.getEntity().getContent(), UsersResponse.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to get data from endpoint " + url, e);
        }
    }

    public List<PostsResponse> getUserPosts(Long id) {
        String url = this.url + "/" + id + "/posts";
        HttpGet httpGet = new HttpGet(url);

        try (CloseableHttpResponse response = httpClient.execute(httpGet)){
            return objectMapper.readValue(response.getEntity().getContent(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, PostsResponse.class));
        } catch (IOException e) {
            throw new RuntimeException("Failed to get data from endpoint " + url, e);
        }
    }

    public List<ToDosResponse> getUserToDos(Long id) {
        String url = this.url + "/" + id + "/todos";
        HttpGet httpGet = new HttpGet(url);

        try (CloseableHttpResponse response = httpClient.execute(httpGet)){
            return objectMapper.readValue(response.getEntity().getContent(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, ToDosResponse.class));
        } catch (IOException e) {
            throw new RuntimeException("Failed to get data from endpoint " + url, e);
        }
    }

    public List<AlbumsResponse> getUserAlbums(Long id) {
        String url = this.url + "/" + id + "/albums";
        HttpGet httpGet = new HttpGet(url);

        try (CloseableHttpResponse response = httpClient.execute(httpGet)){
            return objectMapper.readValue(response.getEntity().getContent(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, AlbumsResponse.class));
        } catch (IOException e) {
            throw new RuntimeException("Failed to get data from endpoint " + url, e);
        }
    }

    public void deleteUserById(Long id) {
        String url = this.url + "/" + id;
        HttpDelete httpDelete = new HttpDelete(url);

        try (CloseableHttpResponse response = httpClient.execute(httpDelete)){
            objectMapper.readValue(response.getEntity().getContent(), UsersResponse.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete data from endpoint " + url, e);
        }
    }

    public UsersResponse updateUserFieldsById(Long id, UsersRequest usersRequest) {
        String url = this.url + "/" + id;
        HttpPatch httpPatch = new HttpPatch(url);
        httpPatch.setHeader("Content-Type", "application/json");

        StringEntity entity;

        try {
            String json = objectMapper.writeValueAsString(usersRequest);
            entity = new StringEntity(json);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Failed to serialize data to json", e);
        }

        httpPatch.setEntity(entity);

        try (CloseableHttpResponse response = httpClient.execute(httpPatch)){
            return objectMapper.readValue(response.getEntity().getContent(), UsersResponse.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to patch data to endpoint " + url, e);
        }
    }

    public UsersResponse updateUserById(Long id, UsersRequest usersRequest)  {
        String url = this.url + "/" + id;
        HttpPut httpPut = new HttpPut(url);
        httpPut.setHeader("Content-Type", "application/json");

        StringEntity entity;

        try {
            String json = objectMapper.writeValueAsString(usersRequest);
            entity = new StringEntity(json);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Failed to serialize data to json", e);
        }
        httpPut.setEntity(entity);
        try (CloseableHttpResponse response = httpClient.execute(httpPut)){
            return objectMapper.readValue(response.getEntity().getContent(), UsersResponse.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to put data to endpoint " + url, e);
        }
    }

    public UsersResponse addNewUser(UsersRequest usersRequest)  {

        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");

        StringEntity entity;

        try {
            String json = objectMapper.writeValueAsString(usersRequest);
            entity = new StringEntity(json);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Failed to serialize data to json", e);
        }
        httpPost.setEntity(entity);
        try (CloseableHttpResponse response = httpClient.execute(httpPost)){
            return objectMapper.readValue(response.getEntity().getContent(), UsersResponse.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to put data to endpoint " + url, e);
        }
    }
}
