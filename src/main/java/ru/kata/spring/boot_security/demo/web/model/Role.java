package ru.kata.spring.boot_security.demo.web.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @Column
    private Long id;

    @Column
    String role;

    @ManyToMany(mappedBy = "roles")
    @Column
    private Set <User> users;

    public Set <User> getUsers() {
        return users;
    }

    public void setUsers(Set < User > users) {
        this.users = users;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setRole(String description) {
        this.role = description;
    }

    public String getRole() {
        return this.role;
    }

    @Override
    public String getAuthority() {
        return getRole();
    }

    @Override
    public String toString() {
        return this.role;
    }

}
