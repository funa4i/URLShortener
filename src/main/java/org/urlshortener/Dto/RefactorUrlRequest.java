package org.urlshortener.Dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty
    private Long id;

    @Pattern(regexp = "((http:\\/\\/)|(https:\\/\\/)).*")
    private String newFullUrl;

    @Min(1)
    private Integer newIterations
;}
