package org.urlshortener.services;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.urlshortener.Dto.RefactorUrlRequest;
import org.urlshortener.Dto.UrlTransfer;
import org.urlshortener.Dto.UserValid;
import org.urlshortener.Entities.*;
import org.urlshortener.Db.UrlShortenerDb;
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

    @Value("${app.default.createCount}")
    private final Integer DEFAULT_COUNT_PER_DAY;

    @Value("${app.default.urlPath}")
    private final String URL_PATTERN;

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
    public void signUp(@Valid UserValid user){
        db.createUser(userValidMapper.toUser(user), DEFAULT_COUNT_PER_DAY);
    }
    @Override
    public String getLongUrl(@Valid @Pattern(regexp = "([a-z]|[A-Z]|[0-9]){7}") String shortUrl) throws NullObjectException {
        var obj = db.getUrlByShort(shortUrl);
        if (obj.a) {

        }
        return obj.b;
    }
}
