package ru.mastkey.vkbackendtest.jsonPlaceHolder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.client.UsersClient;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.albums.AlbumsResponse;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.posts.PostsResponse;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.users.ToDosResponse;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.users.UsersRequest;
import ru.mastkey.vkbackendtest.jsonPlaceHolder.dto.users.UsersResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "UsersCache")
public class UsersService {
    private final UsersClient usersClient;

    public ResponseEntity<List<UsersResponse>> getAllUsers() {
        List<UsersResponse> users = usersClient.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @Cacheable
    public ResponseEntity<UsersResponse> getUserById(Long id) {
        UsersResponse user = usersClient.getUserById(id);
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<List<PostsResponse>> getUserPosts(Long id) {
        List<PostsResponse> posts = usersClient.getUserPosts(id);
        return ResponseEntity.ok(posts);
    }

    public ResponseEntity<List<ToDosResponse>> getUserToDos(Long id) {
        List<ToDosResponse> todos = usersClient.getUserToDos(id);
        return ResponseEntity.ok(todos);
    }

    public ResponseEntity<List<AlbumsResponse>> getUserAlbums(Long id) {
        List<AlbumsResponse> albums = usersClient.getUserAlbums(id);
        return ResponseEntity.ok(albums);
    }

    @CacheEvict
    public ResponseEntity<?> deleteUserById(Long id) {
        usersClient.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

    @CachePut
    public ResponseEntity<UsersResponse> updateUserFieldsById(Long id, UsersRequest user) {
        UsersResponse userResponse = usersClient.updateUserFieldsById(id, user);
        return ResponseEntity.ok(userResponse);
    }

    @CachePut
    public ResponseEntity<UsersResponse> updateUserById(Long id, UsersRequest user) {
        UsersResponse userResponse = usersClient.updateUserById(id, user);
        return ResponseEntity.ok(userResponse);
    }

    public ResponseEntity<UsersResponse> addNewUser(UsersRequest usersRequest) {
        UsersResponse userResponse = usersClient.addNewUser(usersRequest);
        return ResponseEntity.ok(userResponse);
    }
}
