package ru.mastkey.vkbackendtest.registration.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mastkey.vkbackendtest.entity.Role;
import ru.mastkey.vkbackendtest.entity.User;
import ru.mastkey.vkbackendtest.registration.controller.dto.StatusResponse;
import ru.mastkey.vkbackendtest.registration.controller.dto.UserRegistrationRequest;
import ru.mastkey.vkbackendtest.repositories.RoleRepository;
import ru.mastkey.vkbackendtest.repositories.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<StatusResponse> registration(UserRegistrationRequest request) {
        if (userRepository.findByUsername(request.getUsername()) != null) {
            return ResponseEntity.badRequest().body(new StatusResponse("Username is already taken"));
        }
        User newUser = new User(request.getUsername(), passwordEncoder.encode(request.getPassword()));
        Role role = roleRepository.findByName("ROLE_VIEWER");

        newUser.getRoles().add(role);
        userRepository.save(newUser);
        return ResponseEntity.ok(new StatusResponse("Registration successful"));
    }
}
