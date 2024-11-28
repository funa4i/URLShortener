package org.urlshortener.advice;

import lombok.Data;
import org.urlshortener.enums.ExceptionsAnswers;

import java.time.LocalDateTime;

@Data
public class ExceptionView {


    private ExceptionsAnswers exception;

    private LocalDateTime timestamp;

    public ExceptionView(ExceptionsAnswers exception) {
        this.exception = exception;
        this.timestamp = LocalDateTime.now();
    }
}
