package org.urlshortener.Db.Dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Component
public class UrlDaoImp implements UrlDao {
    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional
    public String getLastUrl(){
        ArrayList<String> obj = (ArrayList<String>) em.createQuery("SELECT shortURL FROM URL ORDER BY id DESC limit 1").getResultList();
        if (obj.isEmpty()){
            return "0000000";
        }
        return obj.get(0);
    }
}
