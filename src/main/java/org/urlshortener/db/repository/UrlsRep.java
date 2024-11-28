package org.urlshortener.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.urlshortener.entities.Url;

import java.util.Optional;

public interface UrlsRep extends JpaRepository<Url, Integer> {

    Optional<Url> getByShortUrl(String shortUrl);

    Optional<Url> getById(Long id);
}
