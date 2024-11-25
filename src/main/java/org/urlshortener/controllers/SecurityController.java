package org.urlshortener.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.urlshortener.enums.ExceptionsAnswers;
import org.urlshortener.exceptions.ObjectAlreadyExists;
import org.urlshortener.services.JwtServ;
import org.urlshortener.services.UserServ;

@RestController
@Slf4j
@RequiredArgsConstructor
public class SecurityController {

    private final UserServ userServ;
    private final JwtServ jwtServ;
    private final AuthenticationManager authenticationManager;


    // Регистрация
    @PostMapping("/auth/signUp")
    public ResponseEntity<?> signUp(@RequestBody UserValid user){
        try {
            log.info("Path /auth/signUp Email: " + user.getEmail());
            userServ.createNewUser(user);
            return ResponseEntity.ok().build();
        }
        catch (ObjectAlreadyExists ex){
            log.warn("Path /auth/singUp Email: " + user.getEmail() + " Exception " + ExceptionsAnswers.USER_ALREADY_EXISTS.name());
            return new ResponseEntity<ExceptionView>(
                    new ExceptionView(ExceptionsAnswers.USER_ALREADY_EXISTS),
                            HttpStatus.valueOf(400)
            );
        }
    }

    // Аутентификация
    @PostMapping("/auth/logIn")
    public ResponseEntity<?> logIn(@RequestBody UserValid user){
        log.info("/auth/logIn Email:" + user.getEmail());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(),
                        user.getPassword() + user.getEmail()));
        UserDetails userDetails = userServ.loadUserByUsername(user.getEmail());
        String token = jwtServ.generateToken(userDetails);
        return  ResponseEntity.ok(new JwtResponse(token));
    }
}
