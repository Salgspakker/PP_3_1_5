package ru.kata.spring.boot_security.demo.web.service;

import ru.kata.spring.boot_security.demo.web.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService  {
    List<User> allUsers();
    void delete(User user);
    void edit(User user);
    Optional<User> getById(Long id);

    User findById(Long id);
    void save(User user);
    User findByUsername(String username);
}
