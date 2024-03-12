package ru.mastkey.vkbackendtest.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import ru.mastkey.vkbackendtest.entity.Role;
import ru.mastkey.vkbackendtest.repositories.RoleRepository;


import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class RolePrivilegeManager {

    private final RoleHierarchy roleHierarchy;

    private final RoleRepository roleRepository;

    public void getPrivilegesForEditorAndSubroles() throws IllegalAccessException {
        Field field = ReflectionUtils.findField(RoleHierarchyImpl.class, "rolesReachableInOneOrMoreStepsMap");
        field.setAccessible(true);
        Map<String, Set<GrantedAuthority>> roleRepresentation = (Map<String, Set<GrantedAuthority>>) field.get(roleHierarchy);

        for (var entry : roleRepresentation.entrySet()) {
            Role higherRole = roleRepository.findByName(entry.getKey());
            for (var authority : entry.getValue()) {
                Role lowerRole = roleRepository.findByName(authority.getAuthority());
                higherRole.getPrivileges().addAll(lowerRole.getPrivileges());
            }
            roleRepository.save(higherRole);
        }
    }
}
