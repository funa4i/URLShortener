package org.urlshortener.Db.Dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.urlshortener.Entities.User;

import java.util.Optional;

@Component
public class UserDaoImp implements UserDao{

}
