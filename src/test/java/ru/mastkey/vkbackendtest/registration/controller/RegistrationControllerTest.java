package ru.mastkey.vkbackendtest.registration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.mastkey.vkbackendtest.registration.controller.dto.StatusResponse;
import ru.mastkey.vkbackendtest.registration.controller.dto.UserRegistrationRequest;
import ru.mastkey.vkbackendtest.registration.service.RegistrationService;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RegistrationController.class)
class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegistrationService registrationService;


    @Test
    @WithMockUser
    void registration() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest("test", "test");
        StatusResponse response = new StatusResponse("Registration successful");

        when(registrationService.registration(request)).thenReturn(ResponseEntity.ok(response));

        mockMvc.perform(post("/api/v1/registration")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}