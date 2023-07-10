package ru.kata.spring.boot_security.demo.web.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.web.model.User;

import java.util.List;

public interface UserService  {
    List<User> allUsers();
    void delete(User user);
    void edit(User user);
    User getById(Long id);
    void save(User user);
    User findByUsername(String username);
}
