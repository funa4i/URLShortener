package org.urlshortener.Excemptions;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ExpiredLinkException extends RuntimeException{

    private String link;

    private String fullUrl;

    @Email
    private String mail;

}
