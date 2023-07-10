package ru.kata.spring.boot_security.demo.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.web.model.Role;
import ru.kata.spring.boot_security.demo.web.model.User;
import ru.kata.spring.boot_security.demo.web.service.RoleService;
import ru.kata.spring.boot_security.demo.web.service.UserService;

import javax.validation.Valid;
import java.util.*;

@Controller
public class UserController {

    private final UserService userService;
    @Autowired
    private RoleService roleService;
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
        model.addAttribute("roles", roleService.allRoles());
        return "edit-user";
    }

    @PostMapping(value = "/edit")
    public String editUserPage(@RequestParam(value = "selectedRoles", required = false) String[] selectedRoles, @Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("roles", roleService.allRoles());
            return "edit-user";
        }
        Set<Role> roles = new HashSet<>();
        for (String s: selectedRoles) {
            roles.add(roleService.getRoleByName(s));
        }
        user.setRoles(roles);
        userService.edit(user);
        return "redirect:/admin";
    }

    @GetMapping("/addView")
    public String addUserPage(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.allRoles());
        return "add-user";
    }

    @PostMapping("/add")
    public String addUser(@RequestParam(value = "selectedRoles", required = false) String[] selectedRoles, @ModelAttribute("user") @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("roles", roleService.allRoles());
            return "add-user";
        }
        Set<Role> roles = new HashSet<>();
        for (String s: selectedRoles) {
            roles.add(roleService.getRoleByName(s));
        }
        user.setRoles(roles);
        userService.save(user);
        return "redirect:/admin";
    }
}

