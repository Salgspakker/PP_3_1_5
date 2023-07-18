package ru.kata.spring.boot_security.demo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.web.model.User;
import ru.kata.spring.boot_security.demo.web.service.UserService;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final UserService userService;
    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "")
    public String adminNavigate(ModelMap model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("users", userService.allUsers());
        model.addAttribute("user", user);
        if (model.getAttribute("update_user") == null) {
            model.addAttribute("update_user", user);
        }
        if (model.getAttribute("add_user") == null) {
            model.addAttribute("add_user", user);
        }
        return "admin";
    }

    @RequestMapping(value = "/delete/{id}")
    private String deleteUserRequest(@PathVariable(name = "id") String id){
        userService.delete(userService.getById(Long.valueOf(id)));
        return "redirect:/admin";
    }

    @PostMapping(value = "/add")
    public String addUserRequest(@Valid @ModelAttribute("add_user") User addUser, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            model.addAttribute("addError", true);
            model.addAttribute("add_user", addUser);
            return adminNavigate(model);
        }
        userService.edit(addUser);
        return "redirect:/admin";
    }
    @PostMapping(value = "/{id}/edit")
    public String editUserRequest(@Valid @ModelAttribute("update_user") User updateUser, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            model.addAttribute("hasErrors", true);
            model.addAttribute("update_user", updateUser);
            return adminNavigate(model);
        }
        userService.edit(updateUser);
        return "redirect:/admin";
    }
}
