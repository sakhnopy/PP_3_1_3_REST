package ru.kata.spring.boot_security.dao;

import ru.kata.spring.boot_security.model.Role;

import java.util.List;

public interface RoleDAO {
    void saveRole(Role role);
    List<Role> getAllRoles();
    Role getRole(String name);
}
