package org.urlshortener.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.urlshortener.Dto.UrlTransfer;
import org.urlshortener.services.UrlShortenerServ;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UrlShortenerServ srv;

    @GetMapping("/{url}")
    private ResponseEntity<String> getLong(@PathVariable("url") String shortUrl){
        return ResponseEntity.status(303).header("Location", srv.getLongUrl(shortUrl)).body("");
    }

    @PostMapping("/short")
    @ResponseStatus(value = HttpStatus.OK)
    private UrlTransfer getShortenUrl(@RequestBody UrlTransfer url) {
        return srv.getNewShortURL(url, SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
