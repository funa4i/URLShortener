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
import org.urlshortener.Enums.Roles;
import org.urlshortener.Excemptions.AccessRightsException;
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


    @Value("${app.default.urlPath}")
    private String URL_PATTERN;

    @Override
    public UrlTransfer getNewShortURL(@Valid UrlTransfer longURL, @Email String userEmail) throws NullObjectException {
        longURL.setMail(userEmail);
        return urlTransferMapper.toUrlTransfer(db.createUrl(urlTransferMapper.toUrl(longURL)), URL_PATTERN);
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
    public void changeCurrentUrl(Long id, RefactorUrlRequest urlEntity) {
        db.saveUrl(id, urlEntity);
    }

    @Override
    public void deleteUrl(Long id) {
        db.deleteUrl(id);
    }

    @Override
    public void setUserRole(String fromWho, Long toId, Roles newRole) {

        var Fr = db.getUserRole(fromWho);
        var Sr = db.getUserRole(toId);
        if (Fr.getValue() <= Sr.getValue()
            || newRole.getValue() > Fr.getValue())
        {
            throw new AccessRightsException(Fr.name());
        }
        db.setUserRole(toId, newRole);
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
