package ru.mastkey.vkbackendtest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mastkey.vkbackendtest.entity.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Privilege findByName(String name);}
