package ru.kata.spring.boot_security.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.model.User;

import java.util.List;



public interface UserService extends UserDetailsService {
    void save(User user);
    void updateUser(User update);
    void removeUserById(int id);
    List<User> getAllUsers();
    User getUserById(int id);
    User getUserByUsername(String username);
}
