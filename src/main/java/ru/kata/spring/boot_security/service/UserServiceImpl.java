package ru.kata.spring.boot_security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.dao.UserDAO;
import ru.kata.spring.boot_security.model.User;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;


@Service
public class UserServiceImpl implements UserService{

    private PasswordEncoder passwordEncoder;
    private final UserDAO userDAO;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserDAO userDAO) {
        this.passwordEncoder = passwordEncoder;
        this.userDAO = userDAO;
    }

    @Transactional
    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.save(user);
    }
    @Transactional
    @Override
    public void updateUser(User update) {
        if (!update.getPassword().equals(Objects.requireNonNull(userDAO.getUserById(update.getId())).getPassword())){
            update.setPassword(passwordEncoder.encode(update.getPassword()));
        }else {
            update.setPassword(Objects.requireNonNull(userDAO.getUserById(update.getId())).getPassword());
        }
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
