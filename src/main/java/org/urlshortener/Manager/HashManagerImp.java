package org.urlshortener.Manager;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class HashManagerImp implements HashManager{

    @Override
    public String getHashFrom(String str, String salt) {
        return BCrypt.hashpw(str + salt, BCrypt.gensalt());
    }
}
