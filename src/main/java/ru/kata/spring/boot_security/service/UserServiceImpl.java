package ru.kata.spring.boot_security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.dao.UserDAO;
import ru.kata.spring.boot_security.model.Role;
import ru.kata.spring.boot_security.model.User;


import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    @Transactional
    @Override
    public void save(User user) {
        User userFromDB = userDAO.getUserByUsername(user.getUsername());
        userFromDB.setRoles(Collections.singleton(new Role(1, "ROLE_USER")));
        userFromDB.setPassword(user.getPassword());
        userDAO.save(user);
    }
    @Transactional
    @Override
    public void updateUser(User update) {
        userDAO.updateUser(update);
    }
    @Transactional
    @Override
    public void removeUserById(int id) {
        userDAO.removeUserById(id);
    }
    @Transactional
    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
    @Transactional
    @Override
    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }
    @Transactional
    @Override
    public User getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
