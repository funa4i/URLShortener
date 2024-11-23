package org.urlshortener.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RefactorUrlRequest {

    @Pattern(regexp = "((http:\\/\\/)|(https:\\/\\/)).*")
    private String newFullUrl;

    private Integer newIterations
;}
