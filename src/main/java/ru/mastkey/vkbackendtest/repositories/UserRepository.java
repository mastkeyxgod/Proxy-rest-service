package ru.mastkey.vkbackendtest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.mastkey.vkbackendtest.entity.User;
import ru.mastkey.vkbackendtest.exception.UserNotFoundException;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username) throws UserNotFoundException;

    @Modifying
    @Query(value = "INSERT INTO users_roles (user_id, role_id) VALUES (:userId, :roleId)", nativeQuery = true)
    void addUserNewRole(Long userId, Long roleId);

    @Modifying
    @Query(value = "DELETE FROM users_roles WHERE user_id = :userId AND role_id = :roleId", nativeQuery = true)
    void removeUserRole(Long userId, Long roleId);

    boolean existsByUsername(String username);
}
