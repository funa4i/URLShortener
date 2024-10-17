package org.urlshortener.Db.Dao;

import org.urlshortener.Enums.Roles;

public interface UserDao {

    Roles getUSerRole(Long id);
    Roles getUSerRole(String mail);
}
