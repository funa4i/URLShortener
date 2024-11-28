package org.urlshortener.advice;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.AccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.urlshortener.enums.ExceptionsAnswers;
import org.urlshortener.exceptions.AttemptCountException;
import org.urlshortener.exceptions.NullObjectException;


@Slf4j
@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class, NullObjectException.class} )
    public  ResponseEntity<ExceptionView> notExistHandler(Exception ex){
        ExceptionView exc = null;
        log.warn(ex.getMessage());
        if (ex instanceof ConstraintViolationException){
            exc = new ExceptionView(ExceptionsAnswers.INVALID_DATA_FORMAT);
        }
        else if (ex instanceof  NullObjectException){
            exc = new ExceptionView(ExceptionsAnswers.REQUIRED_OBJECT_NOT_FOUND);
        }
        return ResponseEntity
                .status(404)
                .body(exc);
    }

    @ExceptionHandler(AttemptCountException.class)
    public ResponseEntity<ExceptionView> attemptsOver(Exception ex){
        log.warn(ex.getMessage());
        var exc = new ExceptionView(ExceptionsAnswers.CREATE_ATTEMPTS_ENDED);
        return ResponseEntity
                .status(400)
                .body(exc);
    }


    @ExceptionHandler({UnsupportedJwtException.class, ExpiredJwtException.class})
    public ResponseEntity<ExceptionView> badJwt(Exception ex){
        log.warn(ex.getMessage());
        var exc = new ExceptionView(ExceptionsAnswers.BAD_JWT_TOKEN);
        return ResponseEntity
                .status(401)
                .body(exc);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionView> badAuth(Exception ex){
        log.warn(ex.getMessage());
        return ResponseEntity.status(401)
                .header("message", "Bad auth")
                .body(new ExceptionView(ExceptionsAnswers.BAD_AUTH));
    }

    @ExceptionHandler(AccessException.class)
    public ResponseEntity<ExceptionView> notAllowed(Exception ex){
        log.warn(ex.getMessage());
        return ResponseEntity.status(403)
                .header("message", "Bad Role")
                .body(new ExceptionView(ExceptionsAnswers.NO_ACCESS_RIGHTS));
    }
}
