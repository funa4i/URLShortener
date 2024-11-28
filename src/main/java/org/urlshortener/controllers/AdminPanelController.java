package org.urlshortener.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.urlshortener.dto.RefactorUrlRequest;
import org.urlshortener.dto.RoleChangeRequest;
import org.urlshortener.entities.Url;
import org.urlshortener.entities.User;
import org.urlshortener.services.UrlShortenerServ;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Admin API")
public class AdminPanelController {

    private final UrlShortenerServ srv;

    // Все юзеры
    @Operation(summary = "Gets all users", tags = "Admin")
    @GetMapping("/admin/users")
    public Page<User> getUsers(
            @RequestParam(name = "page",  required = false ,defaultValue = "${app.default.page.page}")  Integer page,
            @RequestParam(name = "limits", required = false, defaultValue = "${app.default.page.limits}")  Integer limits
    ) throws JsonProcessingException {
        log.info("Path /admin/users. Page: "+  page + " " + "Limits: " + limits);
        var obj = srv.getUsers((Integer) page, (Integer) limits);
        log.debug("Path /admin/users returns object " + (new ObjectMapper()).writeValueAsString(obj));
        return obj;
    }

    // Все ссылки
    @Operation(summary = "Gets all urls", tags = "Admin")
    @GetMapping("/admin/urls")
    public Page<Url> getUrls(
            @RequestParam(name = "page",  required = false ,defaultValue = "${app.default.page.page}")  Integer page,
            @RequestParam(name = "limits", required = false, defaultValue = "${app.default.page.limits}")  Integer limits
    ) throws JsonProcessingException {

        log.info("Path /admin/urls Page: "+  page + " " + "Limits: " + limits);
        var obj = srv.getUrls(page, limits);
        log.debug("Path /admin/urls returns object" + (new ObjectMapper()).writeValueAsString(obj));
        return obj;
    }

    // Удалить ссылку
    @Operation(summary = "Delete definite link", tags = "Admin")
    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping("/admin/urls/{id}")
    public void delUrl(@PathVariable("id") Long id){

        log.info("Path /admin/urls/" + id);
        srv.deleteUrl(id);
    }

    // Сохранить изменение

    @Operation(summary = "Change definite url", tags = "Admin")
    @PatchMapping("/admin/linkChange/{id}")
    public void saveUrl(@RequestBody RefactorUrlRequest changeUrl, @PathVariable("id") Long id) {
        log.info("Path /admin/linkChange/" + id);
        srv.changeCurrentUrl(id, changeUrl);
    }

    //Изменить роль

    @Operation(summary = "Change definite user role", tags = "Admin")
    @PostMapping("/admin/userRole/{id}")
    public void setRole(@RequestBody RoleChangeRequest request, @PathVariable("id") Long id) {
        log.info("Path /admin/userRole/" + id);
        srv.setUserRole(SecurityContextHolder.getContext().getAuthentication().getName(), id, request.getNewRole());
    }
}
