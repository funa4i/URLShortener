package org.urlshortener.Db.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.urlshortener.Entities.User;

import java.util.Optional;

public interface UserRep extends JpaRepository<User, Integer> {

}
