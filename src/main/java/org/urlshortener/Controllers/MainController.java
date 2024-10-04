package org.urlshortener.Controllers;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.urlshortener.services.URLShortenerServ;
import org.urlshortener.Dto.*;

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
    @ResponseStatus(value = HttpStatus.OK)
    private UrlDto getShortenUrl(@RequestBody UrlDto url){
        return new UrlDto(srv.getNewShortURL(url, "lyhov.tim09@gmail.com"));
    }



    @PatchMapping("/iterations/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    private String setIterationsCount(@PathVariable("id") int newIter){
        srv.setShortUrlIterationsForNewUrls(newIter);
        return null;
    }


    @GetMapping("/{url}")
    private ResponseEntity<String> getLong(@PathVariable("url") String shortUrl){
        HttpHeaders responseHeaders = new HttpHeaders();
        return ResponseEntity.status(303).header("Location", srv.getLongUrl(shortUrl)).body("");
    }

}
