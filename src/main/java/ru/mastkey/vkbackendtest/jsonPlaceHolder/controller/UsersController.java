package ru.mastkey.vkbackendtest.jsonPlaceHolder.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.albums.AlbumsResponse;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.posts.PostsResponse;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.users.ToDosResponse;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.users.UsersRequest;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.users.UsersResponse;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.service.UsersService;


import java.util.List;


@RestController
@RequestMapping("/api/v1/jph")
@Slf4j
@RequiredArgsConstructor
@Tag(description = "Контроллер для перенаправления запросов на https://jsonplaceholder.typicode.com/users/**", name = "Users Controller")
public class UsersController {

    private final UsersService usersService;


    @Operation(
            summary = "Получение всех пользователей(Authorization: READ_USERS_PRIVILEGE)",
            description = "Позволяет получить спискок всех пользователей"
    )
    @GetMapping("/users")
    public ResponseEntity<List<UsersResponse>> getAllUsers() {
        return usersService.getAllUsers();
    }


    @Operation(
            summary = "Получение пользователя(Authorization: READ_USERS_PRIVILEGE)",
            description = "Позволяет получить конкретного пользователя по его Id"
    )
    @GetMapping("/users/{id}")
    public ResponseEntity<UsersResponse>  getUserById(@PathVariable @Parameter(description = "Id пользователя") Long id) {
        return usersService.getUserById(id);
    }


    @Operation(
            summary = "Получение постов пользователя(Authorization: READ_USERS_PRIVILEGE)",
            description = "Позволяет получить список всех постов конкретного пользователя по его Id"
    )
    @GetMapping("/users/{id}/posts")
    public ResponseEntity<List<PostsResponse>>  getUserPosts(@PathVariable @Parameter(description = "Id пользователя") Long id) {
        return usersService.getUserPosts(id);
    }


    @Operation(
            summary = "Получение списка дел пользователя(Authorization: READ_USERS_PRIVILEGE)",
            description = "Позволяет получить весь список дел конкретного пользователя по его Id"
    )
    @GetMapping("/users/{id}/todos")
    public ResponseEntity<List<ToDosResponse>>  getUserTodos(@PathVariable @Parameter(description = "Id пользователя") Long id) {
        return usersService.getUserToDos(id);
    }


    @Operation(
            summary = "Получение альбомов пользователя(Authorization: READ_USERS_PRIVILEGE)",
            description = "Позволяет получить список всех альбомов конкретного пользователя по его Id"
    )
    @GetMapping("/users/{id}/albums")
    public ResponseEntity<List<AlbumsResponse>>  getUserAlbums(@PathVariable @Parameter(description = "Id пользователя") Long id) {
        return usersService.getUserAlbums(id);
    }


    @Operation(
            summary = "Создание пользователя(Authorization: CREATE_USERS_PRIVILEGE)",
            description = "Позволяет создать нового пользователя"
    )
    @PostMapping("/users")
    public ResponseEntity<UsersResponse> addNewUser(@Valid @RequestBody UsersRequest usersRequest) {
        return usersService.addNewUser(usersRequest);
    }


    @Operation(
            summary = "удаление пользователя(Authorization: DElETE_USERS_PRIVILEGE)",
            description = "Позволяет удалить конкретного пользователя по его Id"
    )
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable @Parameter(description = "Id пользователя") Long id) {
        return usersService.deleteUserById(id);
    }


    @Operation(
            summary = "Обновление пользователя(Authorization: UPDATE_USERS_PRIVILEGE)",
            description = "Позволяет полность обновить пользователя"
    )
    @PutMapping("/users/{id}")
    public ResponseEntity<UsersResponse> updateUserById(@PathVariable @Parameter(description = "Id пользователя") Long id, @Valid@RequestBody UsersRequest user) {
        return usersService.updateUserById(id, user);
    }


    @Operation(
            summary = "Обновление пользователя(Authorization: UPDATE_USERS_PRIVILEGE)",
            description = "Позволяет обновить конкретные поля пользователя"
    )
    @PatchMapping("/users/{id}")
    public ResponseEntity<UsersResponse> updateUserFieldsById(@PathVariable @Parameter(description = "Id пользователя") Long id, @RequestBody UsersRequest user) {
        return usersService.updateUserFieldsById(id, user);
    }
}
