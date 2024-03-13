package ru.mastkey.vkbackendtest.jsonPlaceHolder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.albums.AlbumsRequest;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.albums.AlbumsResponse;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.albums.PhotosResponse;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.service.AlbumsService;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AlbumsController.class)
class AlbumsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlbumsService albumsService;

    @Test
    @WithMockUser
    void shouldReturnAllAlbums() throws Exception {
        AlbumsResponse response = new AlbumsResponse(1, 1, "test");
        List<AlbumsResponse> albumsResponses = Collections.singletonList(response);
        when(albumsService.getAllAlbums()).thenReturn(albumsResponses);

        mockMvc.perform(get("/api/v1/jph/albums")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userId", Matchers.is(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].title", Matchers.is("test")));

    }

    @Test
    @WithMockUser
    void shouldReturnAlbumById() throws Exception {
        Long id = 1L;
        AlbumsResponse response = new AlbumsResponse(1, 1, "test");

        when(albumsService.getAlbumById(id)).thenReturn(response);

        mockMvc.perform(get("/api/v1/jph/albums/{id}", id)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", Matchers.is(1)))
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.title", Matchers.is("test")));

    }

    @Test
    @WithMockUser
    void shouldReturnAllAlbumPhotosByAlbumId() throws Exception {
        Long id = 1L;
        PhotosResponse response = new PhotosResponse(1, 1, "test", "test", "test");
        List<PhotosResponse> photosResponses = Collections.singletonList(response);

        when(albumsService.getPhotosByAlbumId(id)).thenReturn(photosResponses);

        mockMvc.perform(get("/api/v1/jph/albums/{id}/photos", id)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].albumId", Matchers.is(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].title", Matchers.is("test")))
                .andExpect(jsonPath("$[0].url", Matchers.is("test")))
                .andExpect(jsonPath("$[0].thumbnailUrl", Matchers.is("test")));

    }

    @Test
    @WithMockUser
    void shouldSaveNewAlbumAndReturnNewAlbum() throws Exception {
        AlbumsRequest request = new AlbumsRequest(1, "test");

        AlbumsResponse response = new AlbumsResponse(1, 1, "test");

        when(albumsService.createAlbum(request)).thenReturn(response);

        mockMvc.perform(post("/api/v1/jph/albums")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", Matchers.is(request.getUserId())))
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.title", Matchers.is(request.getTitle())));

    }


    @Test
    @WithMockUser
    void shouldPetUpdateAlbumByIdAndReturnUpdatedAlbum() throws Exception {
        Long id = 1L;
        AlbumsRequest request = new AlbumsRequest(1, "test");
        AlbumsResponse response = new AlbumsResponse(1, 1, "test");

        when(albumsService.updateAlbum(id, request)).thenReturn(response);

        mockMvc.perform(put("/api/v1/jph/albums/{id}", id)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", Matchers.is(request.getUserId())))
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.title", Matchers.is(request.getTitle())));
    }

    @Test
    @WithMockUser
    void shouldPatchAlbumByIdAndReturnUpdatedAlbum() throws Exception {
        Long id = 1L;
        AlbumsRequest request = new AlbumsRequest(null, "test");
        AlbumsResponse response = new AlbumsResponse(1, 1, "test");

        when(albumsService.patchAlbumById(id, request)).thenReturn(response);

        mockMvc.perform(patch("/api/v1/jph/albums/{id}", id)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", Matchers.is(1)))
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.title", Matchers.is(request.getTitle())));
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
