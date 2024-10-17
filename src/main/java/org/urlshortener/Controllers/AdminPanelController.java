package org.urlshortener.Controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.urlshortener.Dto.RefactorUrlRequest;
import org.urlshortener.Dto.RoleChangeRequest;
import org.urlshortener.Entities.Url;
import org.urlshortener.Entities.User;
import org.urlshortener.services.UrlShortenerServ;

@RestController
@RequiredArgsConstructor
public class AdminPanelController {

    private final UrlShortenerServ srv;

    // Все юзеры
    @GetMapping("/admin/users")
    private Page<User> getUsers(
            @RequestParam(name = "page",  required = false ,defaultValue = "${app.default.page.page}")  Integer page,
            @RequestParam(name = "limits", required = false, defaultValue = "${app.default.page.limits}")  Integer limits
    ){
        return srv.getUsers((Integer) page, (Integer) limits);
    }

    // Все ссылки
    @GetMapping("/admin/urls")
    private Page<Url> getUrls(
            @RequestParam(name = "page",  required = false ,defaultValue = "${app.default.page.page}")  Integer page,
            @RequestParam(name = "limits", required = false, defaultValue = "${app.default.page.limits}")  Integer limits
    ) {
        return srv.getUrls(page, limits);
    }

    // Удалить ссылку
    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping("/admin/urls/{id}")
    private void delUrl(@PathVariable("id") Long id){
        srv.deleteUrl(id);
    }

    // Сохранить изменение
    @PatchMapping("/admin/linkChange/{id}")
    private void saveUrl(@RequestBody RefactorUrlRequest changeUrl, @PathVariable("id") Long id) {
        srv.changeCurrentUrl(id, changeUrl);
    }

    //Изменить роль
    @PostMapping("/admin/userRole/{id}")
    private void setRole(@RequestBody RoleChangeRequest request, @PathVariable("id") Long id) {
        srv.setUserRole(SecurityContextHolder.getContext().getAuthentication().getName(), id, request.getNewRole());
    }
}
