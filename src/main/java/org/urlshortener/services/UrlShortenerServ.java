package org.urlshortener.services;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.domain.Page;
import org.urlshortener.Dto.RefactorUrlRequest;
import org.urlshortener.Dto.UrlTransfer;
import org.urlshortener.Dto.UserValid;
import org.urlshortener.Entities.Url;
import org.urlshortener.Entities.User;
import org.urlshortener.Excemptions.NullObjectException;

public interface UrlShortenerServ {
    UrlTransfer getNewShortURL(@Valid UrlTransfer longURL, @Valid String userEmail) throws NullObjectException;

    void signUp(@Valid UserValid user);
    String getLongUrl(@Valid @Pattern(regexp = "([a-z]|[A-Z]|[0-9]){7}")  String shortUrl) throws NullObjectException;

    User getUser(@Min(1) Long id);

    Page<User> getUsers(@Min(0) Integer page, @Min(1) Integer limit);

    Page<Url> getUrls(@Min(0) Integer offset, @Min(1) Integer limit);

    void changeCurrentUrl(@Valid RefactorUrlRequest urlEntity);

    void deleteUrl(@Valid Long id);
}
