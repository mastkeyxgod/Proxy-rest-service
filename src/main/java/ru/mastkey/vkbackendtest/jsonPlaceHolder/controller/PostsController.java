package ru.mastkey.vkbackendtest.jsonPlaceHolder.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.posts.CommentResponse;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.posts.PostsResponse;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.posts.PostsRequest;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.service.PostsService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jph")
@RequiredArgsConstructor
@Tag(description = "Контроллер для перенаправления запросов на https://jsonplaceholder.typicode.com/posts/**", name = "Posts Controller")
public class PostsController {

    private final PostsService postService;

    @Operation(
            summary = "Получение всех постов(Authorization: READ_POSTS_PRIVILEGE)",
            description = "Позволяет получить спискок всех постов"
    )
    @GetMapping("/posts")
    public ResponseEntity<List<PostsResponse>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }


    @Operation(
            summary = "Получение конкретного поста(Authorization: READ_POSTS_PRIVILEGE)",
            description = "Позволяет получить конкретный пост по его Id"
    )
    @GetMapping("/posts/{id}")
    public ResponseEntity<PostsResponse>  getPostById(@PathVariable @Parameter(description = "Id поста") Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }


    @Operation(
            summary = "Получение комментариев поста(Authorization: READ_POSTS_PRIVILEGE)",
            description = "Позволяет получить все комментарии конкретного поста по его Id"
    )
    @GetMapping("/posts/{id}/comments")
    public ResponseEntity<List<CommentResponse>>  getPostCommentsByPostId(@PathVariable @Parameter(description = "Id поста") Long id) {
        return ResponseEntity.ok(postService.getPostCommentsByPostId(id));
    }


    @Operation(
            summary = "Создание поста(Authorization: CREATE_POSTS_PRIVILEGE)",
            description = "Позволяет создать новый пост"
    )
    @PostMapping("/posts")
    public ResponseEntity<?> addNewPost(@Valid @RequestBody PostsRequest request) {
        return ResponseEntity.ok(postService.addNewPost(request));
    }


    @Operation(
            summary = "Удаление поста(Authorization: DELETE_POSTS_PRIVILEGE)",
            description = "Позволяет удалить конкретный пост по его Id"
    )
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<?> deletePostById(@PathVariable @Parameter(description = "Id поста") Long id) {
        postService.deletePostById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @Operation(
            summary = "Обновление поста(Authorization: UPDATE_POSTS_PRIVILEGE)",
            description = "Позволяет обновить конкретный пост по его Id"
    )
    @PutMapping("/posts/{id}")
    public ResponseEntity<PostsResponse> updatePostById(@PathVariable @Parameter(description = "Id поста") Long id, @Valid @RequestBody PostsRequest request) {
        return ResponseEntity.ok(postService.updatePostById(id, request));
    }


    @Operation(
            summary = "Обновление поста(Authorization: UPDATE_POSTS_PRIVILEGE)",
            description = "Позволяет обновить конкретные поля в посте по его Id"
    )
    @PatchMapping("/posts/{id}")
    public ResponseEntity<PostsResponse> updatePostFieldsById(@PathVariable @Parameter(description = "Id поста") Long id, @RequestBody PostsRequest postRequest) {
        return ResponseEntity.ok(postService.updatePostFieldsById(id, postRequest));
    }
}
