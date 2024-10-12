package org.urlshortener.services;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.urlshortener.Dto.RefactorUrlRequest;
import org.urlshortener.Dto.UrlDto;
import org.urlshortener.Dto.UserDto;
import org.urlshortener.Entities.*;
import org.urlshortener.Db.UrlShortenerDb;
import org.urlshortener.Excemptions.NullObjectException;

@Service
@RequiredArgsConstructor
@Validated
public class UrlShortenerServImp implements UrlShortenerServ {

    private final UrlShortenerDb db;

    @Value("${app.default.urlPath}")
    private String urlPatter;

    @Override
    public String getNewShortURL(@Valid UrlDto longURL, @Valid String userEmail) throws NullObjectException {
        return urlPatter + db.createUrl(longURL.getUrl(), longURL.getIterations() ,userEmail);
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
    public void signUp(@Valid UserDto user){
        db.createUser(user);
    }
    @Override
    public String getLongUrl(@Valid @Pattern(regexp = "([a-z]|[A-Z]|[0-9]){7}") String shortUrl) throws NullObjectException {
        var obj = db.getUrlByShort(shortUrl);
        if (obj.a) {

        }
        return obj.b;
    }
}
