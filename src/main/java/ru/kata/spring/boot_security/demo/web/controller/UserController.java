package ru.kata.spring.boot_security.demo.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.web.model.User;
import ru.kata.spring.boot_security.demo.web.service.UserService;

import javax.validation.Valid;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user")
    public String setPage(ModelMap model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(user);
        model.addAttribute("user", userService.findByUsername(user.getUsername()));
        return "user";
    }

    @GetMapping(value = "/admin")
    public String printAllUsers(ModelMap model) {
        model.addAttribute("users", userService.allUsers());
        return "admin";
    }

    @RequestMapping(value = "/delete/{id}")
    private String deleteUser(@PathVariable(name = "id") String id){
        userService.delete(userService.getById(Long.valueOf(id)));
        return "redirect:/admin";
    }

    @GetMapping(value = "/edit/{id}")
    private String editUser(@PathVariable(name = "id") int id, Model model) {
        User user = userService.getById((long) id);
        user.setPassword("");
        model.addAttribute("user", user);
        return "edit-user";
    }

    @PostMapping(value = "/edit")
    public String editUserPage(@Valid @ModelAttribute("user") User user, BindingResult result) {
        if (result.hasErrors()) {
            System.out.println(result);
            return "edit-user";
        }
        userService.edit(user);
        return "redirect:/admin";
    }

    @GetMapping("/addView")
    public String addUserPage(Model model) {
        model.addAttribute("user", new User());
        return "add-user";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "add-user";
        }
        userService.save(user);
        return "redirect:/admin";
    }
}

