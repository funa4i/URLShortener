package org.urlshortener.controller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.urlshortener.Entities.User;
import org.urlshortener.Enums.Roles;
import org.urlshortener.services.URLShortenerServ;
import org.urlshortener.Dto.*;

import java.net.http.HttpResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MainController {
    private final URLShortenerServ srv;
    @PostMapping("/sign-up")
    public void signUp(@RequestBody UserDto user){
        srv.singUp(user); ;
    }


    @PostMapping("/short")
    private String getShortenUrl(@RequestBody UrlCreateRequest url){
        return srv.getNewShortURL(url, "lyhov.tim09@gmail.com");
    }

    @PatchMapping("/iterations/{id}")
    private String setIterationsCount(@PathVariable("id") int newIter){
        srv.setShortUrlIterationsForNewUrls(newIter);
        return null;
    }

    @GetMapping("/{url}")
    private void getLong(@PathVariable("url") String shortUrl){
        srv.getLongUrl(shortUrl);
    }

}
