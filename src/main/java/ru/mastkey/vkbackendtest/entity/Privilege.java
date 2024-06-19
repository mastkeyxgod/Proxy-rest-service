package ru.mastkey.vkbackendtest.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Entity
public class Privilege implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;

    public Privilege(String name) {
        this.name = name;
    }

    public Privilege() {
    }

    @Override
    public String getAuthority() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}
