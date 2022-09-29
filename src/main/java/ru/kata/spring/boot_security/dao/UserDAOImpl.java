package ru.kata.spring.boot_security.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.model.User;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;


@Repository
@Transactional
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User update) {
        entityManager.merge(update);
    }

    @Override
    public void removeUserById(int id) {
        User user =  entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT user from User user", User.class).getResultList();
    }

    @Override
    public User getUserById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User getUserByUsername(String username) {
        return (User) entityManager
                .createQuery("select User from User WHERE username =:theUsername")
                .setParameter("theUsername", username)
                .getSingleResult();
    }
}
