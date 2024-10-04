package org.urlshortener.Advice;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.urlshortener.Excemptions.NullObjectException;

import java.net.http.HttpResponse;

@Slf4j
@RestControllerAdvice
public class ExceptionDBHandler {


    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NullObjectException.class)
    public ResponseEntity<String> some(NullObjectException ex){
        log.error(ex.getMessage(), ex);
        var obf = ex.getStackTrace();
        return ResponseEntity.ok().body(ex.getMessage());
    }
}
