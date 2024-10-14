package org.urlshortener.services;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.urlshortener.Dto.RefactorUrlRequest;
import org.urlshortener.Dto.UrlTransfer;
import org.urlshortener.Dto.UserValid;
import org.urlshortener.Entities.*;
import org.urlshortener.Db.UrlShortenerDb;
import org.urlshortener.Excemptions.ExpiredLinkException;
import org.urlshortener.Excemptions.NullObjectException;
import org.urlshortener.Mappers.IUrlTransferMapper;
import org.urlshortener.Mappers.IUserValidMapper;

@Service
@RequiredArgsConstructor
@Validated
public class UrlShortenerServImp implements UrlShortenerServ {

    private final UrlShortenerDb db;

    private final IUserValidMapper userValidMapper;

    private final IUrlTransferMapper urlTransferMapper;

    private final EmailServ emailSrv;
    @Value("${app.default.createCount}")
    private Integer DEFAULT_COUNT_PER_DAY;

    @Value("${app.default.urlPath}")
    private String URL_PATTERN;

    @Override
    public UrlTransfer getNewShortURL(@Valid UrlTransfer longURL, @Email String userEmail) throws NullObjectException {
        longURL.setMail(userEmail);
        return urlTransferMapper.toUrlTransfer(db.createUrl(urlTransferMapper.toUrl(longURL)), URL_PATTERN);
    }

    @Override
    public User getUser(@Min(1) Long id){
        return db.getUserById(id);
    }

    @Override
    public Page<User> getUsers(@Min(0) Integer page, @Min(1) Integer limit) {
        return db.getAllUsers(page, limit);
    }

    @Override
    public  Page<Url> getUrls(@Min(0) Integer page, @Min(1) Integer limit){
        return db.getAllUrls(page, limit);
    }

    @Override
    public void changeCurrentUrl(@Valid RefactorUrlRequest urlEntity) {
        db.saveUrl(urlEntity);
    }

    @Override
    public void deleteUrl(Long id) {
        db.deleteUrl(id);
    }

    @Override
    public boolean validPassword(UserValid userValid) {
        return db.validUser(userValidMapper.toUser(userValid));
    }

    @Override
    public void signUp(@Valid UserValid user){
        db.createUser(userValidMapper.toUser(user), DEFAULT_COUNT_PER_DAY);
    }
    @Override
    public String getLongUrl(@Valid @Pattern(regexp = "([a-z]|[A-Z]|[0-9]|-){7}") String shortUrl) throws NullObjectException {
        Pair<Boolean, Url> obj;
        try {
            obj = db.getUrlByShort(shortUrl);
        }
        catch (ExpiredLinkException ex){
            emailSrv.expiredUrl(ex.getMail(), URL_PATTERN + ex.getLink(), ex.getFullUrl());
            var exp = new NullObjectException(Url.class.getSimpleName(), ex.getLink());
            exp.setStackTrace(ex.getStackTrace());
            throw exp;
        }

        if (obj.a) {
            emailSrv.expiredUrl(obj.b.getUserMail().getMail(),
                    URL_PATTERN + obj.b.getShortUrl(),
                    obj.b.getFullUrl());
        }
        return obj.b.getFullUrl();
    }
}
