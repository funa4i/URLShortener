package org.urlshortener.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.urlshortener.dto.UrlTransfer;
import org.urlshortener.services.UrlShortenerServ;


@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Application", description = "App API")
public class UserController {

    private final UrlShortenerServ srv;

    // Перейти по ссылке

    @Operation(summary = "Get long url with auto redirect", tags = "Application")
    @GetMapping("/{url}")
    public ResponseEntity<String> getLong(@PathVariable("url") String shortUrl) throws JsonProcessingException {
        log.info("Path /" + shortUrl);
        var obj = srv.getLongUrl(shortUrl);
        log.debug("Path /" + shortUrl + " " + "returns " + (new ObjectMapper().writeValueAsString(obj)));
        return ResponseEntity.status(303).header("Location", obj).body("");

    }

    // Создать новую ссылку

    @Operation(summary = "Create new short link", tags = "Application")
    @PostMapping("/short")
    @ResponseStatus(value = HttpStatus.OK)
    public UrlTransfer getShortenUrl(@RequestBody UrlTransfer url) throws JsonProcessingException {
        log.info("Path /short "  + "Url: " + url.getUrl());
        var obj =  srv.getNewShortURL(url, SecurityContextHolder.getContext().getAuthentication().getName());
        log.debug("Path /short" + "Url: " + url.getUrl() + " returns " + (new ObjectMapper().writeValueAsString(obj)));
        return obj;
    }
}
