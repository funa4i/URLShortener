package org.urlshortener.Controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.urlshortener.Dto.RefactorUrlRequest;
import org.urlshortener.Entities.Url;
import org.urlshortener.Entities.User;
import org.urlshortener.services.UrlShortenerServ;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminPanelController {

    private final UrlShortenerServ srv;

    @GetMapping("/users/{id}")
    private User getUser(@PathVariable("id") Long id){
        return srv.getUser(id);
    }

    @GetMapping("/users")
    private Page<User> getUsers(
            @RequestParam(name = "page",  required = false ,defaultValue = "${app.default.page.page}")  Integer page,
            @RequestParam(name = "limits", required = false, defaultValue = "${app.default.page.limits}")  Integer limits
    ){
        return srv.getUsers((Integer) page, (Integer) limits);
    }

    @GetMapping("/urls")
    private Page<Url> getUrls(
            @RequestParam(name = "page",  required = false ,defaultValue = "${app.default.page.page}")  Integer page,
            @RequestParam(name = "limits", required = false, defaultValue = "${app.default.page.limits}")  Integer limits
    ) {
        return srv.getUrls(page, limits);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping("/urls/{id}")
    private void delUrl(@PathVariable("id") Long id){
        srv.deleteUrl(id);
    }

    @PatchMapping("/linkChange")
    private void saveUrl(@RequestBody RefactorUrlRequest changeUrl){
        srv.changeCurrentUrl(changeUrl);
    }

}
