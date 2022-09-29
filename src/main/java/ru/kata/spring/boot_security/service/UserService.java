package ru.kata.spring.boot_security.service;


import ru.kata.spring.boot_security.model.User;

import java.util.List;



public interface UserService {
    void save(User user);
    void updateUser(User update);
    void removeUserById(int id);
    List<User> getAllUsers();
    User getUserById(int id);
    User getUserByUsername(String username);
}
