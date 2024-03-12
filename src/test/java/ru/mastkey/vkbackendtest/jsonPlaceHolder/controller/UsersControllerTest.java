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
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.albums.AlbumsResponse;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.posts.PostsResponse;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.users.*;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.service.UsersService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsersController.class)
class UsersControllerTest {

    @MockBean
    private UsersService usersService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void shouldReturnAllUsers() throws Exception {
        UsersResponse response = buildNewUsersResponse();
        List<UsersResponse> responseList = List.of(response);

        when(usersService.getAllUsers()).thenReturn(ResponseEntity.ok(responseList));

        mockMvc.perform(get("/api/v1/jph/users")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].website").value("test"));
    }

    @Test
    @WithMockUser
    void shouldReturnUserById() throws Exception {
        Long id = 1L;
        UsersResponse response = buildNewUsersResponse();

        when(usersService.getUserById(id)).thenReturn(ResponseEntity.ok(response));

        mockMvc.perform(get("/api/v1/jph/users/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.website").value("test"));
    }

    @Test
    @WithMockUser
    void shouldReturnAllUserPostById() throws Exception {
        Long id = 1L;
        PostsResponse response = new PostsResponse(1, 1, "test", "test");
        List<PostsResponse> responseList = List.of(response);

        when(usersService.getUserPosts(id)).thenReturn(ResponseEntity.ok(responseList));

        mockMvc.perform(get("/api/v1/jph/users/1/posts")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("test"))
                .andExpect(jsonPath("$[0].body").value("test"))
                .andExpect(jsonPath("$[0].userId").value(1));
    }

    @Test
    @WithMockUser
    void shouldReturnAllUserToDosById() throws Exception {
        Long id = 1L;
        ToDosResponse response = new ToDosResponse(1, 1, "test", true);
        List<ToDosResponse> responseList = List.of(response);

        when(usersService.getUserToDos(id)).thenReturn(ResponseEntity.ok(responseList));

        mockMvc.perform(get("/api/v1/jph/users/1/todos")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("test"))
                .andExpect(jsonPath("$[0].completed").value(true))
                .andExpect(jsonPath("$[0].userId").value(1));
    }

    @Test
    @WithMockUser
    void shouldReturnAllUserAlbumsById() throws Exception {
        Long id = 1L;
        AlbumsResponse response = new AlbumsResponse(1, 1, "test");
        List<AlbumsResponse> responseList = List.of(response);

        when(usersService.getUserAlbums(id)).thenReturn(ResponseEntity.ok(responseList));

        mockMvc.perform(get("/api/v1/jph/users/1/albums")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("test"))
                .andExpect(jsonPath("$[0].userId").value(1));
    }

    @Test
    @WithMockUser
    void shouldSaveNewUserAndReturnNewUser() throws Exception {
        UsersRequest request = buildNewUsersRequest();
        UsersResponse response = buildNewUsersResponse();

        when(usersService.addNewUser(request)).thenReturn(ResponseEntity.ok(response));

        mockMvc.perform(post("/api/v1/jph/users")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.website").value(request.getWebsite()))
                .andExpect(jsonPath("$.username").value(request.getUsername()));
    }

    @Test
    @WithMockUser
    void shouldPutUpdateUserByIdAndReturnUpdatedUser() throws Exception {
        Long id = 1L;
        UsersRequest request = buildNewUsersRequest();
        UsersResponse response = buildNewUsersResponse();

        when(usersService.updateUserById(id, request)).thenReturn(ResponseEntity.ok(response));

        mockMvc.perform(put("/api/v1/jph/users/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.website").value(request.getWebsite()))
                .andExpect(jsonPath("$.username").value(request.getUsername()));
    }

    @Test
    @WithMockUser
    void shouldDeleteUserById() throws Exception {
        Long id = 1L;

        when(usersService.deleteUserById(id)).thenReturn(ResponseEntity.ok(null));

        mockMvc.perform(delete("/api/v1/jph/users/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void shouldPatchUpdateUserByIdAndReturnUpdatedUser() throws Exception {
        Long id = 1L;
        UsersRequest request = buildNewUsersRequest();
        UsersResponse response = buildNewUsersResponse();

        when(usersService.updateUserFieldsById(id, request)).thenReturn(ResponseEntity.ok(response));
        mockMvc.perform(patch("/api/v1/jph/users/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.website").value(request.getWebsite()))
                .andExpect(jsonPath("$.username").value(request.getUsername()));
    }


    private UsersRequest buildNewUsersRequest() {
        UsersRequest request = new UsersRequest();
        request.setName("test");
        request.setUsername("test");
        request.setEmail("test");

        AddressDTO address = new AddressDTO();
        address.setStreet("test");
        address.setSuite("test");
        address.setCity("test");
        address.setZipcode("test");
        address.setGeo(new GeoDTO("1", "1"));
        request.setAddress(address);

        request.setPhone("test");
        request.setWebsite("test");

        CompanyDTO company = new CompanyDTO();
        company.setName("test");
        company.setCatchPhrase("test");
        company.setBs("test");
        request.setCompany(company);

        return request;
    }

    private UsersResponse buildNewUsersResponse() {
        UsersResponse response = new UsersResponse();
        response.setId(1);
        response.setName("test");
        response.setUsername("test");
        response.setEmail("test");

        AddressDTO address = new AddressDTO();
        address.setStreet("test");
        address.setSuite("test");
        address.setCity("test");
        address.setZipcode("test");
        address.setGeo(new GeoDTO("1", "1"));
        response.setAddress(address);

        response.setPhone("test");
        response.setWebsite("test");

        CompanyDTO company = new CompanyDTO();
        company.setName("test");
        company.setCatchPhrase("test");
        company.setBs("test");
        response.setCompany(company);

        return response;
    }



    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}