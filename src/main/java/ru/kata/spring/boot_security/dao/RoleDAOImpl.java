package ru.kata.spring.boot_security.dao;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
@Component
public class RoleDAOImpl implements RoleDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void saveRole(Role role) {
        entityManager.persist(role);
    }

    @Override
    @Transactional
    public List<Role> getAllRoles() {
        TypedQuery<Role> typedQuery = entityManager.createQuery("select r from Role r", Role.class);
        return typedQuery.getResultList();
    }
}
