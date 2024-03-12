package ru.mastkey.vkbackendtest.jsonPlaceHolder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.client.PostsClient;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.posts.CommentResponse;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.posts.PostsResponse;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.posts.PostsRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostsService {

    private final PostsClient postsClient;

    public ResponseEntity<List<PostsResponse>> getAllPosts() {
        List<PostsResponse> posts = postsClient.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    public ResponseEntity<PostsResponse> getPostById(Long id) {
        PostsResponse post = postsClient.getPostById(id);
        return ResponseEntity.ok(post);
    }

    public ResponseEntity<List<CommentResponse>> getPostCommentsByPostId(Long id) {
        List<CommentResponse> comments = postsClient.getPostCommentsByPostId(id);
        return ResponseEntity.ok(comments);
    }

    public ResponseEntity<PostsResponse> addNewPost(PostsRequest postRequest) {

        PostsResponse post = postsClient.addNewPost(postRequest);
        return ResponseEntity.ok(post);
    }

    public ResponseEntity<?> deletePostById(Long id) {
        postsClient.deletePostById(id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<PostsResponse> updatePostById(Long id, PostsRequest postRequest) {
        PostsResponse post = postsClient.updatePostById(id, postRequest);
        return ResponseEntity.ok(post);
    }

    public ResponseEntity<PostsResponse> updatePostFieldsById(Long id, PostsRequest postRequest) {
        PostsResponse post = postsClient.updatePostFieldsById(id, postRequest);
        return ResponseEntity.ok(post);
    }
}
