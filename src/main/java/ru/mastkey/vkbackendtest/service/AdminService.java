package ru.mastkey.vkbackendtest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mastkey.vkbackendtest.dto.AddOrRemoveUserRoleRequest;
import ru.mastkey.vkbackendtest.entity.Role;
import ru.mastkey.vkbackendtest.entity.User;
import ru.mastkey.vkbackendtest.dto.StatusResponse;
import ru.mastkey.vkbackendtest.repositories.RoleRepository;
import ru.mastkey.vkbackendtest.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public StatusResponse addRoleToUser(AddOrRemoveUserRoleRequest request) {
        User user = userRepository.findByUsername(request.getUsername());

        if (user == null) {
            return new StatusResponse("User not found");
        }

        Role role = roleRepository.findByName(request.getRoleName());

        if (role == null) {
            return new StatusResponse("Role not found");
        }

        for (Role userRole : user.getRoles()) {
            if (userRole.getName().equals(role.getName())) {
                return new StatusResponse("User already has this role");
            }
        }

        userRepository.addUserNewRole(user.getId(), role.getId());

        return new StatusResponse("Role added");
    }

    public StatusResponse removeRoleFromUser(AddOrRemoveUserRoleRequest request) {
        User user = userRepository.findByUsername(request.getUsername());

        if (user == null) {
            return new StatusResponse("User not found");
        }

        Role role = roleRepository.findByName(request.getRoleName());

        if (role == null) {
            return new StatusResponse("Role not found");
        }

        userRepository.removeUserRole(user.getId(), role.getId());

        return new StatusResponse("Role removed");
    }
}
