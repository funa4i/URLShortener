package org.urlshortener.Db.Dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.urlshortener.Enums.Roles;


@Component
public class UserDaoImp implements UserDao{

    @PersistenceContext
    EntityManager em;

    @Override
    public Roles getUSerRole(Long id) {
            return (Roles) em.createQuery("SELECT role FROM User WHERE id = :varId").setParameter("varId", id)
                    .getResultList().getFirst();
    }

    @Override
    public Roles getUSerRole(String mail) {
        return (Roles) em.createQuery("SELECT role FROM User WHERE mail = :mail").setParameter("mail", mail)
                .getResultList().getFirst();
    }
}
