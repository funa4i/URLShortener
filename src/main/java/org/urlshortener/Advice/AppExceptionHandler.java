package org.urlshortener.Advice;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.urlshortener.Excemptions.AttemptCountException;
import org.urlshortener.Excemptions.NullObjectException;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class, NullObjectException.class} )
    public  ResponseEntity<ExceptionView> notExistHandler(Exception ex){
        var exc = new ExceptionView(404, ex.getMessage());
        return ResponseEntity
                .status(404)
                .header("message", ex.getMessage())
                .body(exc);
    }

    @ExceptionHandler(AttemptCountException.class)
    public ResponseEntity<ExceptionView> attemptsOver(Exception ex){
        var exc = new ExceptionView(400, ex.getMessage());
        return ResponseEntity
                .status(400)
                .header("message", ex.getMessage())
                .body(exc);
    }


    @ExceptionHandler({UnsupportedJwtException.class, ExpiredJwtException.class})
    public ResponseEntity<ExceptionView> badJwt(Exception ex){
        var exc = new ExceptionView(401, ex.getMessage());
        return ResponseEntity
                .status(401)
                .header("message", "Bad Jwt token")
                .body(exc);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionView> badAuth(Exception ex){
        return ResponseEntity.status(401)
                .header("message", "Bad auth")
                .body(new ExceptionView(401, ex.getMessage()));
    }
}
