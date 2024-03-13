package ru.mastkey.vkbackendtest.jsonPlaceHolder.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.posts.CommentResponse;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.posts.PostsResponse;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.posts.PostsRequest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostsClient {

    @Value("${posts.url}")
    private String url;

    private final ObjectMapper objectMapper;

    private final CloseableHttpClient httpClient;

    public List<PostsResponse> getAllPosts() {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-Type", "application/json");
        try (CloseableHttpResponse response = httpClient.execute(httpGet)){
            return objectMapper.readValue(response.getEntity().getContent(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, PostsResponse.class));
        } catch (IOException e) {
            throw new RuntimeException("Failed to get data from endpoint " + url, e);
        }
    }

    public PostsResponse getPostById(Long id) {
        String url = this.url + "/" + id;
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-Type", "application/json");
        try (CloseableHttpResponse response = httpClient.execute(httpGet)){
            return objectMapper.readValue(response.getEntity().getContent(), PostsResponse.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to get data from endpoint " + url, e);
        }
    }

    public List<CommentResponse> getPostCommentsByPostId(Long id) {
        String url = this.url + "/" + id + "/comments";
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-Type", "application/json");

        try (CloseableHttpResponse response = httpClient.execute(httpGet)){
            return objectMapper.readValue(response.getEntity().getContent(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, CommentResponse.class));
        } catch (IOException e) {
            throw new RuntimeException("Failed to get data from endpoint " + url, e);
        }
    }


    public PostsResponse addNewPost(PostsRequest postRequest) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");

        StringEntity entity;

        try {
            String json = objectMapper.writeValueAsString(postRequest);
            entity = new StringEntity(json);
        } catch (UnsupportedEncodingException | JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize data to json", e);
        }

        httpPost.setEntity(entity);

        try (CloseableHttpResponse response = httpClient.execute(httpPost)){
            return objectMapper.readValue(response.getEntity().getContent(), PostsResponse.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to post data to endpoint " + url, e);
        }
    }

    public void deletePostById(Long id) {
        String url = this.url + "/" + id;
        HttpDelete httpDelete = new HttpDelete(url);
        httpDelete.setHeader("Content-Type", "application/json");

        try (CloseableHttpResponse response = httpClient.execute(httpDelete)){
            if (response.getStatusLine().getStatusCode()!= 200) {
                throw new RuntimeException("Error while deleting post");
            }
        } catch (IOException e) {
            throw new RuntimeException("failed to delete post", e);
        }
    }

    public PostsResponse updatePostById(Long id, PostsRequest postRequest) {
        String url = this.url + "/" + id;
        HttpPut httpPut = new HttpPut(url);
        httpPut.setHeader("Content-Type", "application/json");

        StringEntity entity;
        try {
            entity = new StringEntity(objectMapper.writeValueAsString(postRequest));
        } catch (UnsupportedEncodingException | JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize data to json", e);
        }

        httpPut.setEntity(entity);
        try (CloseableHttpResponse response = httpClient.execute(httpPut)){
            return objectMapper.readValue(response.getEntity().getContent(), PostsResponse.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to put data to endpoint " + url, e);
        }
    }

    public PostsResponse updatePostFieldsById(Long id, PostsRequest postRequest) {
        String url = this.url + "/" + id;
        HttpPatch httpPatch = new HttpPatch(url);
        httpPatch.setHeader("Content-Type", "application/json");


        StringEntity entity;
        try {
            entity = new StringEntity(objectMapper.writeValueAsString(postRequest));
        } catch (UnsupportedEncodingException | JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize data to json", e);
        }

        httpPatch.setEntity(entity);
        try (CloseableHttpResponse response = httpClient.execute(httpPatch)){
            return objectMapper.readValue(response.getEntity().getContent(), PostsResponse.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to patch data to endpoint " + url, e);
        }
    }
}
