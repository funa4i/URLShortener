package org.urlshortener.Db.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.urlshortener.Entities.URL;

import java.util.Optional;

public interface UrlsRep extends JpaRepository<URL, Integer> {

    Optional<URL> getByShortURL(String shortUrl);
}
