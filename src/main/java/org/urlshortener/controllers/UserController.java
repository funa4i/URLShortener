package org.urlshortener.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.urlshortener.dto.UrlTransfer;
import org.urlshortener.services.UrlShortenerServ;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UrlShortenerServ srv;

    // Перейти по ссылке
    @GetMapping("/{url}")
    public ResponseEntity<String> getLong(@PathVariable("url") String shortUrl){
        return ResponseEntity.status(303).header("Location", srv.getLongUrl(shortUrl)).body("");
    }

    // Создать новую ссылку
    @PostMapping("/short")
    @ResponseStatus(value = HttpStatus.OK)
    public UrlTransfer getShortenUrl(@RequestBody UrlTransfer url) {
        return srv.getNewShortURL(url, SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
