package org.urlshortener.Db.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.urlshortener.Entities.Url;

import java.util.Optional;

public interface UrlsRep extends JpaRepository<Url, Integer> {

    Optional<Url> getByShortURL(String shortUrl);

    Optional<Url> getById(Long id);
}
