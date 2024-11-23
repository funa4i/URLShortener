package org.urlshortener.services;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.domain.Page;
import org.urlshortener.dto.RefactorUrlRequest;
import org.urlshortener.dto.UrlTransfer;
import org.urlshortener.entities.Url;
import org.urlshortener.entities.User;
import org.urlshortener.enums.Roles;
import org.urlshortener.exceptions.NullObjectException;

public interface UrlShortenerServ {

    // Новый url
    UrlTransfer getNewShortURL(@Valid UrlTransfer longURL, @Email String userEmail) throws NullObjectException;

    // Достать по короткой ссылке
    String getLongUrl(@Valid @Pattern(regexp = "([a-z]|[A-Z]|[0-9]|-){7}")  String shortUrl) throws NullObjectException;

    // все юзеры
    Page<User> getUsers(@Min(0) Integer page, @Min(1) Integer limit);

    // все ссылки
    Page<Url> getUrls(@Min(0) Integer offset, @Min(1) Integer limit);

    // поменять url
    void changeCurrentUrl(Long id, @Valid RefactorUrlRequest urlEntity);

    // удалить url
    void deleteUrl(@Valid Long id);

    // поменять роль user
    void setUserRole(String fromWho, Long toId, Roles newRole);
}
