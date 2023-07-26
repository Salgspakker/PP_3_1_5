package ru.kata.spring.boot_security.demo.web.model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.demo.web.validation.UniqueField;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@UniqueField
public class User implements UserDetails {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="username")
    @NotEmpty(message = "Username cannot be empty.")
    @Pattern(regexp="^[A-ZА-Яa-zа-я]*$",message = "Invalid Input")
    private String username;

    @Column(name = "name")
    @NotEmpty(message = "Name cannot be empty.")
    @Pattern(regexp="^[A-ZА-Яa-zа-я]*$",message = "Invalid Input")
    private String name;

    @Column(name = "age")
    @NotNull(message = "Age cannot be empty")
    @Min(value = 1, message = "User's age cannot be less than 1.")
    @Max(value = 150, message = "User's age cannot be over 150.")
    private Integer age;

    @Column(name = "password")
    private String password;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user"),
            inverseJoinColumns = @JoinColumn(name = "role")
    )
    private Set<Role> roles = new HashSet<>();

    public User() {}

    public User(Long id, @NotNull String username, @NotNull String name, int age) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull String getUsername() {
        return this.username;
    }

    public void setUsername(@NotNull String username) {
        this.username = username;
    }

    public @NotNull(message = "Age cannot be empty") @Min(value = 1, message = "User's age cannot be less than 1.") @Max(value = 150, message = "User's age cannot be over 150.") Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public @NotNull String getName() {
        return this.name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public void setRoles (Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String toString() {
        return this.id + " " + this.username + " " + this.name + " " + this.age + " ";
    }
}


