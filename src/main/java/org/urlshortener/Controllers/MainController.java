package org.urlshortener.Controllers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.urlshortener.Entities.Url;
import org.urlshortener.Entities.User;
import org.urlshortener.services.UrlShortenerServ;
import org.urlshortener.Dto.*;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final UrlShortenerServ srv;

    // da
    @GetMapping("/{url}")
    private ResponseEntity<String> getLong(@PathVariable("url") String shortUrl){
        return ResponseEntity.status(303).header("Location", srv.getLongUrl(shortUrl)).body("");
    }

    // da
    @PostMapping("/signUp")
    public void signUp(@RequestBody UserValid user){
        srv.signUp(user);
    }

    // da
    @PostMapping("/short")
    @ResponseStatus(value = HttpStatus.OK)
    private UrlTransfer getShortenUrl(@RequestBody UrlTransfer url) {
        var mail = "lyhov.tim09@gmail.com";
        return srv.getNewShortURL(url, mail);
    }

    //da
    @GetMapping("/users/{id}")
    private User getUser(@PathVariable("id") Long id){
        return srv.getUser(id);
    }
    // da
    @GetMapping("/users")
    private Page<User> getUsers(
           @RequestParam(name = "page",  required = false ,defaultValue = "${app.default.page.page}")  Integer page,
           @RequestParam(name = "limits", required = false, defaultValue = "${app.default.page.limits}")  Integer limits
    ){
        return srv.getUsers((Integer) page, (Integer) limits);
    }
    // da
    @GetMapping("/urls")
    private Page<Url> getUrls(
            @RequestParam(name = "page",  required = false ,defaultValue = "${app.default.page.page}")  Integer page,
            @RequestParam(name = "limits", required = false, defaultValue = "${app.default.page.limits}")  Integer limits
    ) {
        return srv.getUrls(page, limits);
    }

    // da
    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping("/urls/{id}")
    private void delUrl(@PathVariable("id") Long id){
        srv.deleteUrl(id);
    }

    // da
    @PatchMapping("/linkChange")
    private void saveUrl(@RequestBody RefactorUrlRequest changeUrl){
        srv.changeCurrentUrl(changeUrl);
    }


}



