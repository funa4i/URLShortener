package org.urlshortener.db;

import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.Pair;
import org.antlr.v4.runtime.misc.Triple;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.urlshortener.db.dao.UrlDao;
import org.urlshortener.db.dao.UserDao;
import org.urlshortener.db.repository.UrlsRep;
import org.urlshortener.db.repository.UserRep;
import org.urlshortener.dto.RefactorUrlRequest;
import org.urlshortener.entities.Url;
import org.urlshortener.entities.User;
import org.urlshortener.enums.Roles;
import org.urlshortener.exceptions.AttemptCountException;
import org.urlshortener.exceptions.ExpiredLinkException;
import org.urlshortener.exceptions.NullObjectException;
import org.urlshortener.exceptions.ObjectAlreadyExists;
import org.urlshortener.manager.HashManagerImp;
import org.urlshortener.manager.ShortUrlManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UrlShortenerDb {

    private final ShortUrlManager urlManager;

    private final UrlDao urls;

    private final UserDao users;

    private final UrlsRep urlRep;

    private final UserRep userRep;

    private final HashManagerImp hash;

    @Value(value = "${app.default.linkDurationDays}")
    private Long linkDurationDays;


    // Создание нового юзера
    @Transactional
    public void createUser(User newUser, Integer countsPerDay){
        if (userRep.getByMail(newUser.getMail()).isPresent()){
            throw new ObjectAlreadyExists(newUser.getMail(), User.class.getSimpleName());
        }
        newUser.setMaxLinkAvail(countsPerDay);
        newUser.setCreateLinksLeft(countsPerDay);
        newUser.setRole(Roles.USER);
        userRep.save(newUser);
    }

    // Все протухшие ссылки (mail, shorLink, longLink)
    @Transactional
    public List<Triple<String, String,String>> getAllExpiredLinks(){
        ArrayList<Triple<String, String, String>> resList = new ArrayList<>();
        urls.getMailOfExpiredLinks().forEach((x) ->resList.add(new Triple<>(((User)x[0]).getMail(),(String) x[1],(String) x[2])));
        return resList;
    }

    // Удалить все протухшее
    @Transactional
    public void deleteExpiredLinks(){
        urls.deleteExpiredLinks();
    }

    // Юзера по mail
    @Transactional
    private User getUserByMail(String mail) throws NullObjectException {
        var obj = userRep.getByMail(mail);
        if (obj.isEmpty()){
            throw new NullObjectException(User.class.getSimpleName(), mail);
        }
        return obj.get();
    }

    // Создать url
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Url createUrl(Url url) throws NullObjectException {
        var user = getUserByMail(url.getUser().getMail());

        if (LocalDateTime.now().isAfter(user.getLastCreate().plusDays(1))){
            user.setCreateLinksLeft(user.getMaxLinkAvail());
            user.setLastCreate(LocalDateTime.now());
        }
        if(user.getCreateLinksLeft() <= 0){
            throw new AttemptCountException(user.getLastCreate().plusDays(1));
        }
        url.setUser(user);
        var shorturl = "";
        do {
            shorturl = urlManager.getNextValue();
        }while (urlRep.getByShortUrl(shorturl).isPresent());
        user.decreaseCreatedLinks();
        userRep.save(user);
        url.setShortUrl(urlManager.getNextValue());
        url.setValidUntil(LocalDateTime.now().plusDays(linkDurationDays));
        urlRep.save(url);
        return url;
    }

    // Роль юзера по id
    @Transactional
    public Roles getUserRole(Long id){
        return users.getUSerRole(id).orElseThrow(
                () -> new NullObjectException(User.class.getSimpleName(), id.toString())
        );
    }

    // Роль юзера по mail
    @Transactional
    public Roles getUserRole(String mail){
        return users.getUSerRole(mail).orElseThrow(
                () -> new NullObjectException(User.class.getSimpleName(), mail)
        );
    }

    // Установить роль для юзера
    @Transactional
    public void setUserRole(Long id, Roles role){
         var ob =  userRep.getById(id).orElseThrow(
                 () -> new NullObjectException(User.class.getSimpleName(), id.toString())
         );
         ob.setRole(role);
         userRep.save(ob);
    }

    // Достать по короткой ссылке
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Pair<Boolean,Url> getUrlByShort(String shortUrl) throws ExpiredLinkException {
        var longUrl = urlRep
                .getByShortUrl(shortUrl)
                .orElseThrow(
                () -> new NullObjectException(Url.class.getSimpleName(), shortUrl)
                );
        if (LocalDateTime.now().isAfter(longUrl.getValidUntil())){
            deleteUrl(longUrl);
            throw new ExpiredLinkException(longUrl.getShortUrl(), longUrl.getFullUrl(), longUrl.getUser().getMail());
        }
        longUrl.decreaseIterations();
        if (longUrl.getIterations() <= 0){
            deleteUrl(longUrl);
        }
        else {
            urlRep.save(longUrl);
        }

        return new Pair<>(longUrl.getIterations() <= 0, longUrl) ;
    }


    // Удалить url по id
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteUrl(Long id){
        deleteUrl(urlRep
                .getById(id)
                .orElseThrow(
                () -> new NullObjectException(Url.class.getSimpleName(), id.toString()))
        );
    }

    // Удалить url
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    private void deleteUrl(Url url){
            urlRep.delete(url);
    }

    // Все юзеры
    @Transactional
    public Page<User> getAllUsers(Integer page, Integer limit){
        return userRep.findAll(PageRequest.of(page, limit));
    }

    // Все ссылки
    @Transactional
    public Page<Url> getAllUrls(Integer offset, Integer limit){
        return urlRep.findAll(PageRequest.of(offset, limit));
    }


    // Изменение ссылки
    @Transactional
    public void saveUrl(Long id, RefactorUrlRequest url) {
        Url saveUrl = urlRep
                .getById(id)
                .orElseThrow(
                        () -> new NullObjectException(Url.class.getSimpleName(), id.toString())
        );
        if (url.getNewIterations() != null){
            saveUrl.setIterations(url.getNewIterations());
        }
        if (url.getNewFullUrl() != null){
            saveUrl.setFullUrl(url.getNewFullUrl());
        }
        urlRep.save(saveUrl);
    }

    // Достать юзера по mail
    @Transactional
    public User getUseByMail(String mail){
        return userRep.getByMail(mail).orElseThrow(
                () -> new UsernameNotFoundException("User " + mail + " doesn't found")
        );
    }
}
