package ru.kata.spring.boot_security.demo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.web.exception_handling.NoSuchUserException;
import ru.kata.spring.boot_security.demo.web.model.User;
import ru.kata.spring.boot_security.demo.web.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class UserRestController {
    UserService userService;

    public UserRestController() {

    }

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/info")
    public ResponseEntity<User> getUser(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        if (user == null) {
            throw new NoSuchUserException("No user with name " + principal.getName() + " found in database");
        }
        return ResponseEntity.ok(userService.findByUsername(principal.getName()));
    }
}
