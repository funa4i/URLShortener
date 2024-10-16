package org.urlshortener.Advice;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
        var exc = new ExceptionView(403, ex.getMessage());
        return ResponseEntity
                .status(403)
                .header("message", ex.getMessage())
                .body(exc);
    }
}
