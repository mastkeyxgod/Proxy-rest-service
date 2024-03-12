package ru.mastkey.vkbackendtest.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.mastkey.vkbackendtest.admin.controller.dto.AddOrRemoveUserRoleRequest;
import ru.mastkey.vkbackendtest.admin.service.AdminService;
import ru.mastkey.vkbackendtest.registration.controller.dto.StatusResponse;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @MockBean
    private AdminService adminService;

    @Autowired
    private MockMvc mockMvc;



    @Test
    @WithMockUser
    void testAddRoleWithAuth() throws Exception {
        AddOrRemoveUserRoleRequest request = new AddOrRemoveUserRoleRequest("username", "ROLE_NAME");
        StatusResponse response = new StatusResponse("Role added successfully");

        when(adminService.addRoleToUser(request)).thenReturn(ResponseEntity.ok(response));

        mockMvc.perform(post("/api/v1/admin/add-role")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testRemoveRoleWithAuth() throws Exception {
        AddOrRemoveUserRoleRequest request = new AddOrRemoveUserRoleRequest("username", "ROLE_NAME");
        StatusResponse response = new StatusResponse("Role removed successfully");

        when(adminService.removeRoleFromUser(request)).thenReturn(ResponseEntity.ok(response));

        mockMvc.perform(post("/api/v1/admin/remove-role")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void testRemoveRoleWithoutAuth() throws Exception {
        AddOrRemoveUserRoleRequest request = new AddOrRemoveUserRoleRequest("username", "ROLE_NAME");
        StatusResponse response = new StatusResponse("Role removed successfully");

        when(adminService.removeRoleFromUser(request)).thenReturn(ResponseEntity.ok(response));

        mockMvc.perform(post("/api/v1/admin/remove-role")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithAnonymousUser
    void testAddRoleWithoutAuth() throws Exception {
        AddOrRemoveUserRoleRequest request = new AddOrRemoveUserRoleRequest("username", "ROLE_NAME");
        StatusResponse response = new StatusResponse("Role added successfully");

        when(adminService.addRoleToUser(request)).thenReturn(ResponseEntity.ok(response));

        mockMvc.perform(post("/api/v1/admin/add-role")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isUnauthorized());
    }


    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

