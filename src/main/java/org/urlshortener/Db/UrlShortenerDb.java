package org.urlshortener.Db;

import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.urlshortener.Db.Dao.UrlDao;
import org.urlshortener.Db.Dao.UserDao;
import org.urlshortener.Db.Repository.UrlsRep;
import org.urlshortener.Db.Repository.UserRep;
import org.urlshortener.Dto.RefactorUrlRequest;
import org.urlshortener.Dto.UserDto;
import org.urlshortener.Entities.Url;
import org.urlshortener.Entities.User;
import org.urlshortener.Enums.Roles;
import org.urlshortener.Excemptions.NullObjectException;
import org.urlshortener.services.HashManagerSha256Imp;
import org.urlshortener.services.ShortUrlManager;

@Component
@RequiredArgsConstructor
public class UrlShortenerDb {

    private final ShortUrlManager urlManager;

    private final UrlDao urls;

    private final UserDao users;

    private final UrlsRep urlRep;

    private final UserRep userRep;

    private final HashManagerSha256Imp hash;

    @Transactional
    public void createUser(UserDto newUser){
        User usr = new User();
        usr.setMaxLinkAvail(newUser.getCountPerDay());
        usr.setCreateLinksLeft(newUser.getCountPerDay());
        usr.setMail(newUser.getEmail());
        usr.setPassword(
                hash.getHashFrom(newUser.getPassword(), newUser.getEmail()
                ));
        usr.setRole(Roles.USER);
        userRep.save(usr);
    }


    @Transactional
    public User getUserByMail(String mail) throws NullObjectException {
        var obj = userRep.getByMail(mail);
        if (obj.isEmpty()){
            throw new NullObjectException(User.class.getSimpleName(), mail);
        }
        return obj.get();
    }

    @Transactional
    public String createUrl(String newUrlSt, Integer iterations, String mail) throws NullObjectException {
        var user = getUserByMail(mail);

        Url newUrl = new Url();

        newUrl.setIterations(iterations);
        newUrl.setUserMail(user);
        newUrl.setFullURL(newUrlSt);

        var shorturl = "";
        do {
            shorturl = urlManager.getNextValue();
        }while (urlRep.getByShortURL(shorturl).isPresent());

        newUrl.setShortURL(urlManager.getNextValue());
        urlRep.save(newUrl);
        return newUrl.getShortURL();
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Pair<Boolean,String> getUrlByShort(String shortUrl) throws NullObjectException {
        var longUrl = urlRep
                .getByShortURL(shortUrl)
                .orElseThrow(
                () -> new NullObjectException(Url.class.getSimpleName(), shortUrl)
                );

        longUrl.decreaseIterations();
        if (longUrl.getIterations() <= 0){
            deleteUrl(longUrl);
        }
        else {
            urlRep.save(longUrl);
        }

        return new Pair<>(longUrl.getIterations() <= 0, longUrl.getFullURL()) ;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteUrl(Long id){
        deleteUrl(urlRep
                .getById(id)
                .orElseThrow(
                () -> new NullObjectException(Url.class.getSimpleName(), id.toString()))
        );
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    private void deleteUrl(Url url){
            urlRep.delete(url);
    }

    @Transactional
    public Page<User> getAllUsers(Integer page, Integer limit){
        return userRep.findAll(PageRequest.of(page, limit));
    }

    @Transactional
    public Page<Url> getAllUrls(Integer offset, Integer limit){
        return urlRep.findAll(PageRequest.of(offset, limit));
    }

    @Transactional
    public User getUserById(Long id) throws NullObjectException{
        return userRep
                .getById(id)
                .orElseThrow(
                () -> new NullObjectException(User.class.getSimpleName(), id.toString())
        );
    }

    @Transactional
    public void saveUrl(RefactorUrlRequest url){
        Url saveUrl = urlRep
                .getById(url.getId())
                .orElseThrow(
                () -> new NullObjectException(Url.class.getSimpleName(), url.getId().toString())
        );
        if (url.getNewIterations() != null){
            saveUrl.setIterations(url.getNewIterations());
        }
        if (url.getNewFullUrl() != null){
            saveUrl.setFullURL(url.getNewFullUrl());
        }
        urlRep.save(saveUrl);
    }
}
