package org.urlshortener.Db;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
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
    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)

    private Integer urlIter = 100;
    private ShortUrlManager lastUrl;

    private final URLDao urls;

    private final UserDao users;

    private final UrlsRep urlRep;

    private final UserRep userRep;

    public void createUser(User newUser){
        userRep.save(newUser);
    }

    public User getUserByMail(String mail){
        return null;
    }

    public void createUrl(URL newUrl, User user){
        if (user.getId() == null) {
            // Добавить ошибку отсутсвия в бд
            return;
        }
        newUrl.setIterations(urlIter);
        newUrl.setUserID(user);
        newUrl.setShortURL(lastUrl.getNextValue());
        urlRep.save(newUrl);
    }
}
