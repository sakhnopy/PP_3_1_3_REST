package ru.kata.spring.boot_security.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.dao.RoleDAO;
import ru.kata.spring.boot_security.model.Role;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Service
public class RoleServiceImpl implements RoleService{

    private final RoleDAO roleDAO;
    public RoleServiceImpl(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Transactional
    @Override
    public void addRole(Role role) {
        roleDAO.saveRole(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleDAO.getAllRoles();
    }

    @Override
    public Set<Role> findRoleById(String roleId) {
        Set<Role> roles = new HashSet<>();
        for (Role role : getAllRoles()){
            if (roleId.contains(role.getId().toString())) {
                roles.add(role);
            }
        }
        return roles;
    }

    @Override
    public Role getRole(String name) {
        return roleDAO.getRole(name);
    }
}
