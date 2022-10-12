package ru.kata.spring.boot_security.dao;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class RoleDAOImpl implements RoleDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveRole(Role role) {
        entityManager.persist(role);
    }

    @Override
    public List<Role> getAllRoles() {
        TypedQuery<Role> typedQuery = entityManager.createQuery("select r from Role r", Role.class);
        return typedQuery.getResultList();
    }
    @Override
    public Role getRole(String name) {
        return entityManager.createQuery("select r from Role r where r.name =: name", Role.class)
                .setParameter("name", name).getSingleResult();
    }
}
