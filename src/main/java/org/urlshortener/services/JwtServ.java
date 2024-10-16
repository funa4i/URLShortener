package org.urlshortener.services;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.security.PrivateECKey;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.urlshortener.Entities.User;
import org.urlshortener.Enums.Roles;

import java.security.Key;
import java.security.PrivateKey;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtServ {

    @Value(value = "${spring.jwt.secret}")
    private String secret;

    @Value(value = "${spring.jwt.duration}")
    private Duration lifeTime;


    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof User){
            claims.put("Role", ((User) userDetails).getRole().name());
        }
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date( new Date().getTime() + lifeTime.toMillis()))
                .signWith(getSigningKey())
                .compact();

    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUserName(String token){
        return getAllClaims(token).getSubject();
    }


    public Roles getRole(String token){
        return getAllClaims(token).get("Role", Roles.class);
    }

    private Claims getAllClaims(String token){
        return Jwts.parser()
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
