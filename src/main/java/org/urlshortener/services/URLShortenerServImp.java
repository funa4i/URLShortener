package org.urlshortener.services;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.urlshortener.Dto.UrlCreateRequest;
import org.urlshortener.Dto.UserDto;
import org.urlshortener.Entities.*;
import org.urlshortener.Db.URLShortenerDb;
import org.urlshortener.Enums.Roles;

@Service
@RequiredArgsConstructor
@Validated
public class URLShortenerServImp implements URLShortenerServ {

    private final URLShortenerDb db;

    private final static String urlPatter = "";

    @Override
    public String getNewShortURL(@Valid UrlCreateRequest longURL, @Valid String userEmail) {
        URL newUrl = new URL();
        newUrl.setFullURL(longURL.getFullUrl());
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
    public String getLongUrl(@Valid @Pattern(regexp = "([a-z]|[A-Z]|[0-9]){7}") String shortUrl) {
        return db.getUrlByShort(shortUrl);
    }
}
