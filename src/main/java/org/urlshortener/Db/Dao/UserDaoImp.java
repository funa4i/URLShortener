package org.urlshortener.Db.Dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.urlshortener.Enums.Roles;


@Component
public class UserDaoImp implements UserDao{

    @PersistenceContext
    EntityManager em;


    // Роль по id юзера
    @Override
    public Roles getUSerRole(Long id) {
            return (Roles) em.createQuery("SELECT role FROM User WHERE id = :varId").setParameter("varId", id)
                    .getResultList().getFirst();
    }

    // Роль по mail юзера
    @Override
    public Roles getUSerRole(String mail) {
        return (Roles) em.createQuery("SELECT role FROM User WHERE mail = :mail").setParameter("mail", mail)
                .getResultList().getFirst();
    }
}
