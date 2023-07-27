package ru.kata.spring.boot_security.demo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.web.exception_handling.NoSuchUserException;
import ru.kata.spring.boot_security.demo.web.exception_handling.UserValidationException;
import ru.kata.spring.boot_security.demo.web.model.User;
import ru.kata.spring.boot_security.demo.web.service.UserService;
import ru.kata.spring.boot_security.demo.web.validation.ValidationResultHandler;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AdminRestController {

    UserService userService;

    public AdminRestController() {

    }

    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> showAllUsers() {
        return ResponseEntity.ok().body(userService.allUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable(name = "id") long id) {
        Optional<User> user = userService.getById(id);
        if (user.isEmpty()) {
            throw new NoSuchUserException("No user with id " + id + " found in database");
        }
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @GetMapping("/users/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable(name = "username") String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new NoSuchUserException("No user with username " + username + " found in database");
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<User> addNewUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ValidationResultHandler validationResultHandler = new ValidationResultHandler();
            throw new UserValidationException(validationResultHandler.handleBindingResult(bindingResult));
        }
        userService.save(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ValidationResultHandler validationResultHandler = new ValidationResultHandler();
            throw new UserValidationException(validationResultHandler.handleBindingResult(bindingResult));
        }
        userService.edit(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        Optional<User> user = userService.getById(id);
        if (user.isEmpty()) {
            throw new NoSuchUserException("User with id " + id + " not found in database");
        }
        userService.delete(user.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
