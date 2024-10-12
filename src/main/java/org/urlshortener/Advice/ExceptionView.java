package org.urlshortener.Advice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionView {

    private Integer status;

    private String message;

    private LocalDateTime timestamp;
}
