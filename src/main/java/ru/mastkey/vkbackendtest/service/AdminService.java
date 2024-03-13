package ru.mastkey.vkbackendtest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<StatusResponse> addRoleToUser(AddOrRemoveUserRoleRequest request) {
        User user = userRepository.findByUsername(request.getUsername());

        if (user == null) {
            return ResponseEntity.badRequest().body(new StatusResponse("User not found"));
        }

        Role role = roleRepository.findByName(request.getRoleName());

        if (role == null) {
            return ResponseEntity.badRequest().body(new StatusResponse("Role not found"));
        }

        for (Role userRole : user.getRoles()) {
            if (userRole.getName().equals(role.getName())) {
                return ResponseEntity.badRequest().body(new StatusResponse("User already has this role"));
            }
        }

        userRepository.addUserNewRole(user.getId(), role.getId());

        return ResponseEntity.ok().body(new StatusResponse("Role added"));
    }

    public ResponseEntity<StatusResponse> removeRoleFromUser(AddOrRemoveUserRoleRequest request) {
        User user = userRepository.findByUsername(request.getUsername());

        if (user == null) {
            return ResponseEntity.badRequest().body(new StatusResponse("User not found"));
        }

        Role role = roleRepository.findByName(request.getRoleName());

        if (role == null) {
            return ResponseEntity.badRequest().body(new StatusResponse("Role not found"));
        }

        userRepository.removeUserRole(user.getId(), role.getId());

        return ResponseEntity.ok(new StatusResponse("Role removed"));
    }
}
