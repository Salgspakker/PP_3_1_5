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
    private final RoleService roleService;
    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping(value = "/user")
    public String userViewNavigate(ModelMap model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(user);
        model.addAttribute("user", userService.findByUsername(user.getUsername()));
        return "user";
    }

    @GetMapping(value = "/admin")
    public String adminViewNavigate(ModelMap model) {
        model.addAttribute("users", userService.allUsers());
        return "admin";
    }

    @RequestMapping(value = "/admin/delete/{id}")
    private String deleteUserRequest(@PathVariable(name = "id") String id){
        userService.delete(userService.getById(Long.valueOf(id)));
        return "redirect:/admin";
    }

    @GetMapping(value = "/admin/edit/{id}")
    private String editUserViewNavigate(@PathVariable(name = "id") int id, Model model) {
        User user = userService.getById((long) id);
        user.setPassword("");
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.allRoles());
        return "edit-user";
    }

    @PostMapping(value = "/admin/edit")
    public String editUserRequest(@RequestParam(value = "selectedRoles", required = false) String[] selectedRoles, @Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
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

    @GetMapping("/admin/addView")
    public String addUserViewNavigate(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.allRoles());
        return "add-user";
    }

    @PostMapping("/admin/add")
    public String addUserRequest(@RequestParam(value = "selectedRoles", required = false) String[] selectedRoles, @ModelAttribute("user") @Valid User user, BindingResult result, Model model) {
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

