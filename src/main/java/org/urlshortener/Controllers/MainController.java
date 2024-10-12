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

@Slf4j
@RestController
@RequiredArgsConstructor
public class MainController {

    @Value("${app.default.page.page}}")
    private final Integer page;

    private final Integer limits;

    private final UrlShortenerServ srv;

    @PostMapping("/sign-up")
    public void signUp(@RequestBody UserValid user){
        srv.signUp(user);
    }

    @PostMapping("/short")
    @ResponseStatus(value = HttpStatus.OK)
    private UrlTransfer getShortenUrl(@RequestBody UrlTransfer url) {
        var mail = "lyhov.tim09@gmail.com";
        return srv.getNewShortURL(url, mail);
    }


    @GetMapping("/users/{id}")
    private User getUser(@PathVariable("id") Long id){
        return srv.getUser(id);
    }

    @PostMapping("/users")
    private Page<User> getUsers(
            @RequestBody(required = false) Integer page,
            @RequestBody(required = false) Integer limits
            ){
        if (page == null){
            page = this.page;
        }
        if (limits == null){
            limits = this.limits;
        }
        return srv.getUsers(page, limits);
    }

    @PostMapping("/urls")
    private Page<Url> getUrls(
            @RequestBody(required = false) Integer page,
            @RequestBody(required = false) Integer limits
    ){
        if (page == null){
            page = this.page;
        }
        if (limits == null){
            limits = this.limits;
        }
        return srv.getUrls(page, limits);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping("/urls/{id}")
    private void delUrl(@PathVariable("id") Long id){
        srv.deleteUrl(id);
    }


    @PatchMapping("/changeUrl")
    private void saveUrl(@RequestBody RefactorUrlRequest changeUrl){
        srv.changeCurrentUrl(changeUrl);
    }

    @GetMapping("/{url}")
    private ResponseEntity<String> getLong(@PathVariable("url") String shortUrl){
        HttpHeaders responseHeaders = new HttpHeaders();
        return ResponseEntity.status(303).header("Location", srv.getLongUrl(shortUrl)).body("");
    }
}



