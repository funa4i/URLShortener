package org.urlshortener.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.urlshortener.entities.User;

import java.util.Optional;

public interface UserRep extends JpaRepository<User, Integer> {

    Optional<User> getByMail(String mail);

    Optional<User> getById(Long id);

}
