package ru.mastkey.vkbackendtest.jsonPlaceHolder.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mastkey.vkbackendtest.entity.User;
import ru.mastkey.vkbackendtest.repositories.UserRepository;

@RestController
@RequestMapping("/test")
public class TestController {

    private final UserRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public TestController(UserRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/{username}")
    public ResponseEntity<Boolean> test(@PathVariable String username) {
        User user = usersRepository.findByUsername(username);
        String password = user.getPassword();
        return ResponseEntity.ok(passwordEncoder.matches("test", password));
    }
}
