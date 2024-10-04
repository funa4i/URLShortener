package org.urlshortener.Advice;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.urlshortener.Excemptions.NullObjectException;

@Slf4j
@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class, NullObjectException.class} )
    public  ResponseEntity<String> baRequestHandler(Exception ex){
        log.error(ex.getMessage(), ex);
        return ResponseEntity.badRequest().header("message", ex.getMessage()).build();
    }
}
