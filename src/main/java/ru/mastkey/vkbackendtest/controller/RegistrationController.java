package ru.mastkey.vkbackendtest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mastkey.vkbackendtest.dto.StatusResponse;
import ru.mastkey.vkbackendtest.dto.UserRegistrationRequest;
import ru.mastkey.vkbackendtest.service.RegistrationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(description = "Контроллер для регистрации новых пользователей в системе", name = "Registry Controller")
public class RegistrationController {

    private final RegistrationService registrationService;

    @Operation(
            summary = "Регистрация нового пользователя",
            description = "Регистрирует нового пользователя в системе"
    )
    @PostMapping("/registration")
    public ResponseEntity<StatusResponse> registration(@Valid @RequestBody UserRegistrationRequest request) {
        return registrationService.registration(request);
    }
}
