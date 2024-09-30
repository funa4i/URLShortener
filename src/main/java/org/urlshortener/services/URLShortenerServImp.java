package org.urlshortener.services;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.urlshortener.Dto.UserDto;
import org.urlshortener.Entities.*;
import org.urlshortener.Db.URLShortenerDb;
import org.urlshortener.Enums.Roles;

@Service
@RequiredArgsConstructor
@Validated
public class URLShortenerServImp implements URLShortenerServ {

    private final URLShortenerDb db;

    @Override
    public String getShortURL(String longURL) {
        return null;
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
}
