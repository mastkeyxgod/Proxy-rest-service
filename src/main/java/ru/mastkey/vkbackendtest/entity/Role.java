package ru.mastkey.vkbackendtest.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;
import java.util.Set;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"
            ))
    private Set<Privilege> privileges;

    public Role(String name) {
        this.name = name;
    }

    public Role() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public Set<Privilege> getPrivileges() {
        return privileges;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    public void setPrivileges(Set<Privilege> privileges) {
        this.privileges = privileges;
    }
}
