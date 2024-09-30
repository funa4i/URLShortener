package org.urlshortener.Db.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.urlshortener.Entities.URL;
public interface UrlsRep extends JpaRepository<URL, Integer> {
}
