package ru.mastkey.vkbackendtest.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mastkey.vkbackendtest.dto.UserRegistrationRequest;
import ru.mastkey.vkbackendtest.entity.Role;
import ru.mastkey.vkbackendtest.entity.User;
import ru.mastkey.vkbackendtest.exception.UserAlreadyExistException;
import ru.mastkey.vkbackendtest.repositories.RoleRepository;
import ru.mastkey.vkbackendtest.repositories.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long registration(UserRegistrationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())){
            throw new UserAlreadyExistException("Username is already taken");
        }
        User newUser = new User(request.getUsername(), passwordEncoder.encode(request.getPassword()));
        log.info(request.getPassword());
        Role role = roleRepository.findByName("ROLE_VIEWER");

        newUser.getRoles().add(role);
        userRepository.save(newUser);
        return newUser.getId();
    }
}
