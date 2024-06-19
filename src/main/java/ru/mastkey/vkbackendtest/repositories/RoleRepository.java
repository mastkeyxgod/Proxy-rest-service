package ru.mastkey.vkbackendtest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mastkey.vkbackendtest.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

    boolean existsByName(String name);
}
