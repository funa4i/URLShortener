package org.urlshortener.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Getter
@Validated
@Setter(value = AccessLevel.PRIVATE)
public class UserValid {

    @Email(message = "Email is not valid")
    private String email;

    @Size(min = 5)
    @Size(max = 255)
    private String password;
}
