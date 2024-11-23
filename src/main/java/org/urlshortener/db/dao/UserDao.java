package org.urlshortener.db.dao;

import org.urlshortener.enums.Roles;

import java.util.Optional;

public interface UserDao {

    Optional<Roles> getUSerRole(Long id);
    Optional<Roles> getUSerRole(String mail);
}
