package org.urlshortener.services;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.urlshortener.Db.Repository.UserRep;
import org.urlshortener.Db.UrlShortenerDb;
import org.urlshortener.Dto.UserValid;
import org.urlshortener.Entities.User;
import org.urlshortener.Mappers.IUserValidMapper;


@Service
@RequiredArgsConstructor
public class UserServ implements UserDetailsService {

    private final UrlShortenerDb db;

    private final IUserValidMapper userValidMapper;

    @Value("${app.default.createCount}")
    private Integer DEFAULT_COUNT_PER_DAY;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return db.getUseByMail(username);
    }

    public void createNewUser(@Valid UserValid user){
       db.createUser(userValidMapper.toUser(user), DEFAULT_COUNT_PER_DAY);
    }
}
