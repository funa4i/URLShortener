package org.urlshortener.services;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.urlshortener.Dto.UrlCreateRequest;
import org.urlshortener.Dto.UserDto;
import org.urlshortener.Entities.*;

public interface URLShortenerServ {
    String getNewShortURL(@Valid UrlCreateRequest longURL, @Valid String userEmail);

    void singUp(@Valid UserDto user);

    void setShortUrlIterationsForNewUrls(@Min(value = 1, message = "The new value must be greater than zero") int count);

    String getLongUrl(@Valid @Pattern(regexp = "([a-z]|[A-Z]|[0-9]){7}")  String shortUrl);
}
