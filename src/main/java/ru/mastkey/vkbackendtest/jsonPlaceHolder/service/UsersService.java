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

    public List<UsersResponse> getAllUsers() {
        List<UsersResponse> users = usersClient.getAllUsers();
        return users;
    }

    @Cacheable
    public UsersResponse getUserById(Long id) {
        UsersResponse user = usersClient.getUserById(id);
        return user;
    }

    public List<PostsResponse> getUserPosts(Long id) {
        List<PostsResponse> posts = usersClient.getUserPosts(id);
        return posts;
    }

    public List<ToDosResponse> getUserToDos(Long id) {
        List<ToDosResponse> todos = usersClient.getUserToDos(id);
        return todos;
    }

    public List<AlbumsResponse> getUserAlbums(Long id) {
        List<AlbumsResponse> albums = usersClient.getUserAlbums(id);
        return albums;
    }

    @CacheEvict
    public void deleteUserById(Long id) {
        usersClient.deleteUserById(id);
    }

    @CachePut
    public UsersResponse updateUserFieldsById(Long id, UsersRequest user) {
        UsersResponse userResponse = usersClient.updateUserFieldsById(id, user);
        return userResponse;
    }

    @CachePut
    public UsersResponse updateUserById(Long id, UsersRequest user) {
        UsersResponse userResponse = usersClient.updateUserById(id, user);
        return userResponse;
    }

    public UsersResponse addNewUser(UsersRequest usersRequest) {
        UsersResponse userResponse = usersClient.addNewUser(usersRequest);
        return userResponse;
    }
}
