package ru.kata.spring.boot_security.demo.web.service;

import ru.kata.spring.boot_security.demo.web.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> allRoles();

    Role getRoleByName(String name);

}
