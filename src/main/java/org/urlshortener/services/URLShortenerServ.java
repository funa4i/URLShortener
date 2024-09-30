package org.urlshortener.services;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.urlshortener.Dto.UserDto;
import org.urlshortener.Entities.*;

public interface URLShortenerServ {
    String getShortURL(String longURL);

    void singUp(@Valid UserDto user);

    void setShortUrlIterationsForNewUrls(@Min(value = 1, message = "The new value must be greater than zero") int count);
}
