package org.urlshortener.Dto;


import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
@AllArgsConstructor
public class UrlDto {

    @Pattern(regexp = "((http:\\/\\/)|(https:\\/\\/)).*")
    private final String Url;
}


