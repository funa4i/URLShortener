package org.urlshortener.Dto;


import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Getter
@Validated
@Setter(value = AccessLevel.PRIVATE)
public class UserValid {
    @Email(message = "Email is not valid")
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @NotEmpty
    @Size(max = 255, message = "Maximum password length 255")
    private String password;
}
