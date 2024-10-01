package org.urlshortener.Db;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.urlshortener.Db.Dao.URLDao;
import org.urlshortener.Db.Dao.UserDao;
import org.urlshortener.Db.Repository.UrlsRep;
import org.urlshortener.Db.Repository.UserRep;
import org.urlshortener.Entities.URL;
import org.urlshortener.Entities.User;
import org.urlshortener.services.ShortUrlManager;
import org.urlshortener.services.ShortUrlManagerImpSequence;

@Component
@RequiredArgsConstructor
public class URLShortenerDb {
    @Getter
    @Setter
    private Integer urlIter = 100;

    private final ShortUrlManager urlManager;

    private final URLDao urls;

    private final UserDao users;

    private final UrlsRep urlRep;

    private final UserRep userRep;

    public void createUser(User newUser){
        userRep.save(newUser);
    }


    @Transactional
    public User getUserByMail(String mail){
        var obj = userRep.getByMail(mail);
        if (obj.isEmpty()){
            // Ошибка отсутствия в бд
            return null;
        }
        return obj.get();
    }

    @Transactional
    public String createUrl(URL newUrl, User user){
        if (user.getId() == null) {
            //ошибка отсутсвия в бд
            return null;
        }
        newUrl.setIterations(urlIter);
        newUrl.setUserID(user);
        var shorturl = "";
        do {
            shorturl = urlManager.getNextValue();
        }while (!urlRep.getByShortURL(shorturl).isEmpty());

        newUrl.setShortURL(urlManager.getNextValue());
        urlRep.save(newUrl);
        return newUrl.getShortURL();
    }

    @Transactional
    public String getUrlByShort(String shortUrl){
        var longurl = urlRep.getByShortURL(shortUrl);
        if (!longurl.isEmpty()){
            return longurl.get().getFullURL();
        }
        //ошибка отсутсвия в бд
        return  null;
    }
}