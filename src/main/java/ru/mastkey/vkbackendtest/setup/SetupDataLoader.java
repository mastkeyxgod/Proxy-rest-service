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

    @Value("${setup.test-users}")
    private boolean testUsers;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final PasswordEncoder passwordEncoder;

    private final RolePrivilegeManager rolePrivilegeManager;

    public SetupDataLoader(UserRepository userRepository, RoleRepository roleRepository, PrivilegeRepository privilegeRepository, PasswordEncoder passwordEncoder, RoleHierarchy roleHierarchy, RolePrivilegeManager rolePrivilegeManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
        this.passwordEncoder = passwordEncoder;
        this.rolePrivilegeManager = rolePrivilegeManager;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (rolesAndPrivileges) {

            Privilege postCreatePrivilege = createPrivilegeIfNotFound("CREATE_POSTS_PRIVILEGE");
            Privilege postUpdatePrivilege = createPrivilegeIfNotFound("UPDATE_POSTS_PRIVILEGE");
            Privilege postDeletePrivilege = createPrivilegeIfNotFound("DELETE_POSTS_PRIVILEGE");

            Privilege albumCreatePrivilege = createPrivilegeIfNotFound("CREATE_ALBUMS_PRIVILEGE");
            Privilege albumUpdatePrivilege = createPrivilegeIfNotFound("UPDATE_ALBUMS_PRIVILEGE");
            Privilege albumDeletePrivilege = createPrivilegeIfNotFound("DELETE_ALBUMS_PRIVILEGE");

            Privilege userCreatePrivilege = createPrivilegeIfNotFound("CREATE_USERS_PRIVILEGE");
            Privilege userUpdatePrivilege = createPrivilegeIfNotFound("UPDATE_USERS_PRIVILEGE");
            Privilege userDeletePrivilege = createPrivilegeIfNotFound("DELETE_USERS_PRIVILEGE");

            Privilege postReadPrivilege = createPrivilegeIfNotFound("READ_POSTS_PRIVILEGE");
            Privilege albumReadPrivilege = createPrivilegeIfNotFound("READ_ALBUMS_PRIVILEGE");
            Privilege userReadPrivilege = createPrivilegeIfNotFound("READ_USERS_PRIVILEGE");

            Privilege webSocketCreatePrivilege = createPrivilegeIfNotFound("WEBSOCKETS_PRIVILEGE");

            createRoleIfNotFound("ROLE_WEBSOCKETS", Set.of(webSocketCreatePrivilege));

            createRoleIfNotFound("ROLE_USERS_VIEWER", Set.of(userReadPrivilege));
            createRoleIfNotFound("ROLE_ALBUMS_VIEWER", Set.of(albumReadPrivilege));
            createRoleIfNotFound("ROLE_POSTS_VIEWER", Set.of(postReadPrivilege));

            createRoleIfNotFound("ROLE_USERS_EDITOR", Set.of(userCreatePrivilege,
                    userUpdatePrivilege, userDeletePrivilege));

            createRoleIfNotFound("ROLE_ALBUMS_EDITOR", Set.of(albumCreatePrivilege,
                    albumUpdatePrivilege, albumDeletePrivilege));

            createRoleIfNotFound("ROLE_POSTS_EDITOR", Set.of(postCreatePrivilege,
                    postUpdatePrivilege, postDeletePrivilege));

            createRoleIfNotFound("ROLE_ADMIN", Collections.emptySet()
            );

            createRoleIfNotFound("ROLE_VIEWER", Collections.emptySet()
            );

            try {
                rolePrivilegeManager.getPrivilegesForEditorAndSubroles();
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }




        if (testUsers) {
            Role webSocketRole = roleRepository.findByName("ROLE_WEBSOCKETS");
            User webSocketUser = new User();
            webSocketUser.setUsername("websocket");
            webSocketUser.setPassword(passwordEncoder.encode("test"));
            webSocketUser.setRoles(Set.of(webSocketRole));


            Role usersEditor = roleRepository.findByName("ROLE_USERS_EDITOR");
            User user = new User();
            user.setUsername("user_editor");
            user.setPassword(passwordEncoder.encode("test"));
            user.setRoles(Set.of(usersEditor));

            Role postsEditor = roleRepository.findByName("ROLE_POSTS_EDITOR");
            User postsEditorUser = new User();
            postsEditorUser.setUsername("post_editor");
            postsEditorUser.setPassword(passwordEncoder.encode("test"));
            postsEditorUser.setRoles(Set.of(postsEditor));

            Role albumsEditor = roleRepository.findByName("ROLE_ALBUMS_EDITOR");
            User albumsEditorUser = new User();
            albumsEditorUser.setUsername("album_editor");
            albumsEditorUser.setPassword(passwordEncoder.encode("test"));
            albumsEditorUser.setRoles(Set.of(albumsEditor));

            Role usersViewer = roleRepository.findByName("ROLE_USERS_VIEWER");
            User userViewer = new User();
            userViewer.setUsername("user_viewer");
            userViewer.setPassword(passwordEncoder.encode("test"));
            userViewer.setRoles(Set.of(usersViewer));

            Role clown = roleRepository.findByName("ROLE_VIEWER");
            User clownUser = new User();
            clownUser.setUsername("viewer");
            clownUser.setPassword(passwordEncoder.encode("test"));
            clownUser.setRoles(Set.of(clown));

            Role admin = roleRepository.findByName("ROLE_ADMIN");
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("test"));
            adminUser.setRoles(Set.of(admin));

            userRepository.save(adminUser);
            userRepository.save(albumsEditorUser);
            userRepository.save(user);
            userRepository.save(userViewer);
            userRepository.save(clownUser);
            userRepository.save(postsEditorUser);
            userRepository.save(webSocketUser);

        }


        rolesAndPrivileges = false;
        testUsers = false;

        log.info("Setup complete");
        log.info("testUsers: " + testUsers);
        log.info("rolesAndPrivileges: " + rolesAndPrivileges);

    }

    Privilege createPrivilegeIfNotFound(String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    Role createRoleIfNotFound(String name, Set<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}
