package org.urlshortener.Manager;

import com.google.common.hash.Hashing;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class HashManagerImp implements HashManager{

    @Override
    public String getHashFrom(String str, String salt) {
        return BCrypt.hashpw(str + salt, BCrypt.gensalt());
    }
}
