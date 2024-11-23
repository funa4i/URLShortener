package org.urlshortener.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.urlshortener.dto.RefactorUrlRequest;
import org.urlshortener.dto.RoleChangeRequest;
import org.urlshortener.entities.Url;
import org.urlshortener.entities.User;
import org.urlshortener.services.UrlShortenerServ;

@RestController
@RequiredArgsConstructor
public class AdminPanelController {

    private final UrlShortenerServ srv;

    // Все юзеры
    @GetMapping("/admin/users")
    public Page<User> getUsers(
            @RequestParam(name = "page",  required = false ,defaultValue = "${app.default.page.page}")  Integer page,
            @RequestParam(name = "limits", required = false, defaultValue = "${app.default.page.limits}")  Integer limits
    ){
        return srv.getUsers((Integer) page, (Integer) limits);
    }

    // Все ссылки
    @GetMapping("/admin/urls")
    public Page<Url> getUrls(
            @RequestParam(name = "page",  required = false ,defaultValue = "${app.default.page.page}")  Integer page,
            @RequestParam(name = "limits", required = false, defaultValue = "${app.default.page.limits}")  Integer limits
    ) {
        return srv.getUrls(page, limits);
    }

    // Удалить ссылку
    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping("/admin/urls/{id}")
    public void delUrl(@PathVariable("id") Long id){
        srv.deleteUrl(id);
    }

    // Сохранить изменение
    @PatchMapping("/admin/linkChange/{id}")
    public void saveUrl(@RequestBody RefactorUrlRequest changeUrl, @PathVariable("id") Long id) {
        srv.changeCurrentUrl(id, changeUrl);
    }

    //Изменить роль
    @PostMapping("/admin/userRole/{id}")
    public void setRole(@RequestBody RoleChangeRequest request, @PathVariable("id") Long id) {
        srv.setUserRole(SecurityContextHolder.getContext().getAuthentication().getName(), id, request.getNewRole());
    }
}
