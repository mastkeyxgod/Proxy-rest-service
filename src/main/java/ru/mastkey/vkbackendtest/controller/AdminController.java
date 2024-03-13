package ru.mastkey.vkbackendtest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mastkey.vkbackendtest.dto.AddOrRemoveUserRoleRequest;
import ru.mastkey.vkbackendtest.service.AdminService;
import ru.mastkey.vkbackendtest.dto.StatusResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@Tag(description = "Администрирование", name = "Admin Controller")
public class AdminController {

    private final AdminService adminService;


    @Operation(
            summary = "Добавление роли(Authorization: ROLE_ADMIN)",
            description = "Позволяет добавить роль пользователю по его username и roleName"
    )
    @PostMapping("/add-role")
    public ResponseEntity<StatusResponse> addRole(@RequestBody AddOrRemoveUserRoleRequest request) {

        return adminService.addRoleToUser(request);
    }

    @Operation(
            summary = "Удаление роли(Authorization: ROLE_ADMIN)",
            description = "Позволяет удалить роль пользователя по его username и roleName"
    )
    @PostMapping("/remove-role")
    public ResponseEntity<StatusResponse> removeRole(@RequestBody AddOrRemoveUserRoleRequest request) {

        return adminService.removeRoleFromUser(request);
    }
}
