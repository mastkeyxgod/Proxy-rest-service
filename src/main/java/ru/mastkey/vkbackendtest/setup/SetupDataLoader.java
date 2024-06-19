package ru.mastkey.vkbackendtest.setup;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.mastkey.vkbackendtest.entity.Privilege;
import ru.mastkey.vkbackendtest.entity.Role;
import ru.mastkey.vkbackendtest.entity.User;
import ru.mastkey.vkbackendtest.repositories.PrivilegeRepository;
import ru.mastkey.vkbackendtest.repositories.RoleRepository;
import ru.mastkey.vkbackendtest.repositories.UserRepository;
import ru.mastkey.vkbackendtest.security.RolePrivilegeManager;


import java.util.Collections;
import java.util.Set;

@Component
@Slf4j
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${setup.roles-and-privileges}")
    private boolean rolesAndPrivileges;

    private final RolePrivilegeManager rolePrivilegeManager;

    public SetupDataLoader(RolePrivilegeManager rolePrivilegeManager) {
        this.rolePrivilegeManager = rolePrivilegeManager;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (rolesAndPrivileges) {
            try {
                rolePrivilegeManager.getPrivilegesForEditorAndSubroles();
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        rolesAndPrivileges = false;

        log.info("Setup complete");
        log.info("rolesAndPrivileges: " + rolesAndPrivileges);

    }
}
