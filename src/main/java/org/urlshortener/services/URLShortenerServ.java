package org.urlshortener.services;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.urlshortener.Dto.UrlDto;
import org.urlshortener.Dto.UserDto;
import org.urlshortener.Excemptions.NullObjectException;

public interface URLShortenerServ {
    String getNewShortURL(@Valid UrlDto longURL, @Valid String userEmail) throws NullObjectException;

    void singUp(@Valid UserDto user);

    void setShortUrlIterationsForNewUrls(@Min(value = 1, message = "The new value must be greater than zero") int count);

    String getLongUrl(@Valid @Pattern(regexp = "([a-z]|[A-Z]|[0-9]){7}")  String shortUrl) throws NullObjectException;
}
