package org.urlshortener.Manager;

import com.google.common.hash.Hashing;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class HashManagerSha256Imp implements HashManager {

    @Override
    public String getHashFrom(String str, String salt) {
        var hashSalt = Hashing.sha256()
                .hashString(salt, StandardCharsets.UTF_8)
                .toString();
        return Hashing.sha256()
                .hashString(str + hashSalt, StandardCharsets.UTF_8)
                .toString();
    }
}
