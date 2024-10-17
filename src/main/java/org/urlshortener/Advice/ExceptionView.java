package org.urlshortener.Advice;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExceptionView {

    private Integer status;

    private String message;

    private LocalDateTime timestamp;

    public ExceptionView(Integer status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
