package ru.mastkey.vkbackendtest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.mastkey.vkbackendtest.dto.AddOrRemoveUserRoleRequest;
import ru.mastkey.vkbackendtest.entity.Role;
import ru.mastkey.vkbackendtest.entity.User;
import ru.mastkey.vkbackendtest.dto.StatusResponse;
import ru.mastkey.vkbackendtest.exception.RoleNotFoundException;
import ru.mastkey.vkbackendtest.exception.UserNotFoundException;
import ru.mastkey.vkbackendtest.repositories.RoleRepository;
import ru.mastkey.vkbackendtest.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public void addRoleToUser(AddOrRemoveUserRoleRequest request) {
        Role role = checkRole(request.getRoleName());
        User user  = checkUser(request.getUsername());

        userRepository.addUserNewRole(user.getId(), role.getId());

    }

    public void removeRoleFromUser(AddOrRemoveUserRoleRequest request) {
        Role role = checkRole(request.getRoleName());
        User user  = checkUser(request.getUsername());

        userRepository.removeUserRole(user.getId(), role.getId());
    }

    private Role checkRole(String roleName)  {
        Role role = roleRepository.findByName(roleName);
        if  (role  == null) {
            throw new RoleNotFoundException("Role not found");
        }
        return role;
    }

    private User checkUser(String username)   {
        User user = userRepository.findByUsername(username);
        if  (user  == null) {
            throw new UserNotFoundException("User not found");
        }
        return user;
    }

}
