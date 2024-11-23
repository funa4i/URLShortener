package org.urlshortener.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.urlshortener.advice.ExceptionView;
import org.urlshortener.dto.JwtResponse;
import org.urlshortener.dto.UserValid;
import org.urlshortener.exceptions.ObjectAlreadyExists;
import org.urlshortener.services.JwtServ;
import org.urlshortener.services.UserServ;

@RestController
@RequiredArgsConstructor
public class SecurityController {

    private final UserServ userServ;
    private final JwtServ jwtServ;
    private final AuthenticationManager authenticationManager;


    // Регистрация
    @PostMapping("/auth/signUp")
    public ResponseEntity<?> signUp(@RequestBody UserValid user){
        try {
            userServ.createNewUser(user);
            return ResponseEntity.ok().build();
        }
        catch (ObjectAlreadyExists ex){
            return new ResponseEntity<ExceptionView>(
                    new ExceptionView(400, ex.getMessage()),
                            HttpStatus.valueOf(400)
            );
        }
    }

    // Аутентификация
    @PostMapping("/auth/logIn")
    public ResponseEntity<?> logIn(@RequestBody UserValid user){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(),
                        user.getPassword() + user.getEmail()));
        UserDetails userDetails = userServ.loadUserByUsername(user.getEmail());
        String token = jwtServ.generateToken(userDetails);
        return  ResponseEntity.ok(new JwtResponse(token));
    }
}
