package org.urlshortener.services;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.urlshortener.Dto.UrlDto;
import org.urlshortener.Dto.UserDto;
import org.urlshortener.Entities.*;
import org.urlshortener.Db.URLShortenerDb;
import org.urlshortener.Enums.Roles;
import org.urlshortener.Excemptions.NullObjectException;

@Service
@RequiredArgsConstructor
@Validated
public class URLShortenerServImp implements URLShortenerServ {

    private final URLShortenerDb db;

    private final static String urlPatter = "http://localhost:8080/";

    @Override
    public String getNewShortURL(@Valid UrlDto longURL, @Valid String userEmail) throws NullObjectException {
        URL newUrl = new URL();
        newUrl.setFullURL(longURL.getUrl());
        return urlPatter + db.createUrl(newUrl, db.getUserByMail(userEmail));
    }



    @Override
    public void singUp(@Valid UserDto user){
        User usr = new User();
        usr.setMail(user.getEmail());
        // Сделать шифрование
        usr.setPassword(user.getPassword());
        usr.setRole(Roles.USER);
        db.createUser(usr);
    }

    @Override
    public void setShortUrlIterationsForNewUrls(@Min(value = 1, message = "The new value must be greater than zero") int count) {
        db.setUrlIter(count);
    }

    @Override
    public String getLongUrl(@Valid @Pattern(regexp = "([a-z]|[A-Z]|[0-9]){7}") String shortUrl) throws NullObjectException {
        return db.getUrlByShort(shortUrl);
    }
}
