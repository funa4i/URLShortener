package org.urlshortener.db.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class UserDaoImp implements UserDao{

    @PersistenceContext
    EntityManager em;


    // Роль по id юзера
    @Override
    public Optional getUSerRole(Long id) {
            return em.createQuery("SELECT role FROM User WHERE id = :varId").setParameter("varId", id)
                    .getResultStream().findFirst();
    }

    // Роль по mail юзера
    @Override
    public Optional getUSerRole(String mail) {
        return em.createQuery("SELECT role FROM User WHERE mail = :mail").setParameter("mail", mail)
                .getResultStream().findFirst();
    }
}
