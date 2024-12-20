package org.urlshortener.db.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class UrlDaoImp implements UrlDao {
    @PersistenceContext
    EntityManager em;

    //Последний созданыый url
    @Override
    @Transactional
    public String getLastUrl(){
        List<String> obj = (List<String>) em.createQuery("SELECT shortURL FROM Url ORDER BY id DESC limit 1").getResultList();
        if (obj.isEmpty()){
            return "0000000";
        }
        return obj.get(0);
    }

    // Mail всех протухших ссылок
    @Override
    @Transactional
    public List getMailOfExpiredLinks(){
        return  em.createQuery("SELECT userMail, shortUrl, fullUrl  FROM Url WHERE validUntil < :time")
                .setParameter("time", LocalDateTime.now())
                .getResultList();

    }

    // Удалить все протухшие ссылки
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteExpiredLinks() {
        em.createQuery("DELETE FROM Url WHERE validUntil < :time").setParameter("time", LocalDateTime.now()).executeUpdate();
    }

}
