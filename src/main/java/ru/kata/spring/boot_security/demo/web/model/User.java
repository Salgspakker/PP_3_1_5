package ru.kata.spring.boot_security.demo.web.model;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="username")
    @NotNull(message = "User's name cannot be empty.")
    @Pattern(regexp="^[A-ZА-Я][a-zа-я]*$",message = "Invalid Input")
    private String username;

    @Column(name = "lastname")
    @NotNull(message = "User's Last Name cannot be empty.")
    @Pattern(regexp="^[A-ZА-Я][a-zа-я]*$",message = "Invalid Input")
    private String lastname;

    @Column(name = "age")
    @Min(value = 1, message = "User's age cannot be less than 1.")
    @Max(value = 150, message = "User's age cannot be over 150.")
    private int age;

    @Column(name = "password")
    @NotNull
    private String password;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user"),
            inverseJoinColumns = @JoinColumn(name = "role")
    )
    private Set<Role> roles = new HashSet<>();

    public User() {}

    public User(int id, @NotNull String username, @NotNull String lastname, int age) {
        this.id = id;
        this.username = username;
        this.lastname = lastname;
        this.age = age;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotNull String getUsername() {
        return this.username;
    }

    public void setUsername(@NotNull String name) {
        this.username = name;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public @NotNull String getLastname() {
        return this.lastname;
    }

    public void setLastname(@NotNull String lastName) {
        this.lastname = lastName;
    }

    public void setRoles (Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return this.id + " " + this.username + " " + this.lastname + " " + this.age + " ";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return this.password;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


