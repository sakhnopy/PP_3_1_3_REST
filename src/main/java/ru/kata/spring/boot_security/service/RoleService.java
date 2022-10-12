package ru.kata.spring.boot_security.service;

import ru.kata.spring.boot_security.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    void addRole(Role role);
    List<Role> getAllRoles();
    Set<Role> findRoleById(String roleId);
    Role getRole(String name);
}
