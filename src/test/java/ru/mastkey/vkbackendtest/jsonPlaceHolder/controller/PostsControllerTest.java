package ru.mastkey.vkbackendtest.jsonPlaceHolder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.posts.CommentResponse;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.posts.PostsRequest;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.posts.PostsResponse;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.service.PostsService;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostsController.class)
class PostsControllerTest {

    @MockBean
    private PostsService postsService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void shouldReturnAllPosts() throws Exception {
        PostsResponse postsResponse = new PostsResponse(1, 1, "test", "test");
        List<PostsResponse> postsResponseList = Collections.singletonList(postsResponse);

        when(postsService.getAllPosts()).thenReturn(ResponseEntity.ok(postsResponseList));

        mockMvc.perform(get("/api/v1/jph/posts")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userId").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("test"))
                .andExpect(jsonPath("$[0].body").value("test"));

    }

    @Test
    @WithMockUser
    void shouldReturnPostById() throws Exception {
        Long id = 1L;
        PostsResponse postsResponse = new PostsResponse(1, 1, "test", "test");

        when(postsService.getPostById(id)).thenReturn(ResponseEntity.ok(postsResponse));

        mockMvc.perform(get("/api/v1/jph/posts/{id}", id)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("test"))
                .andExpect(jsonPath("$.body").value("test"));
    }

    @Test
    @WithMockUser
    void shouldReturnAllPostsCommentsByPostId() throws Exception {
        CommentResponse commentResponse = new CommentResponse(1, 1, "test", "test", "test");
        List<CommentResponse> commentResponseList = Collections.singletonList(commentResponse);
        Long postId = 1L;

        when(postsService.getPostCommentsByPostId(postId)).thenReturn(ResponseEntity.ok(commentResponseList));

        mockMvc.perform(get("/api/v1/jph/posts/{id}/comments", postId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].postId").value(commentResponse.getPostId()))
                .andExpect(jsonPath("$[0].id").value(commentResponse.getId()))
                .andExpect(jsonPath("$[0].name").value(commentResponse.getEmail()))
                .andExpect(jsonPath("$[0].email").value(commentResponse.getEmail()))
                .andExpect(jsonPath("$[0].body").value(commentResponse.getBody()));
    }

    @Test
    @WithMockUser
    void shouldDeletePostById() throws Exception {
        Long id = 1L;

        when(postsService.deletePostById(id)).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(get("/api/v1/jph/posts/{id}", id)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void shouldCreateNewPostAndReturnCreatedPost() throws Exception {
        PostsRequest postsRequest = new PostsRequest(1,  "test", "test");
        PostsResponse postsResponse = new PostsResponse(1, 1, "test", "test");

        when(postsService.addNewPost(postsRequest)).thenReturn(ResponseEntity.ok(postsResponse));

        mockMvc.perform(post("/api/v1/jph/posts")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postsRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(postsRequest.getUserId()))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value(postsRequest.getTitle()))
                .andExpect(jsonPath("$.body").value(postsRequest.getBody()));
    }

    @Test
    @WithMockUser
    void shouldPutUpdatePostByIdAndReturnUpdatedPost() throws Exception {
        PostsRequest postsRequest = new PostsRequest(1,  "test", "test");
        PostsResponse postsResponse = new PostsResponse(1, 1, "test", "test");
        Long id = 1L;

        when(postsService.updatePostById(id, postsRequest)).thenReturn(ResponseEntity.ok(postsResponse));

        mockMvc.perform(put("/api/v1/jph/posts/{id}", id)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(postsRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(postsRequest.getUserId()))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value(postsRequest.getTitle()))
                .andExpect(jsonPath("$.body").value(postsRequest.getBody()));
    }

    @Test
    @WithMockUser
    void shouldPatchUpdatePostByIdAndReturnUpdatedPost() throws Exception {
        PostsRequest postsRequest = new PostsRequest(null,  "test", null);
        PostsResponse postsResponse = new PostsResponse(1, 1, "test", "test");
        Long id = 1L;

        when(postsService.updatePostFieldsById(id, postsRequest)).thenReturn(ResponseEntity.ok(postsResponse));

        mockMvc.perform(patch("/api/v1/jph/posts/{id}", id)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(postsRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value(postsRequest.getTitle()))
                .andExpect(jsonPath("$.body").value("test"));

    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}