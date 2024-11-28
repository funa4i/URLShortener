package org.urlshortener.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

@Schema(description = "Refactor link request")
public class RefactorUrlRequest {


    @Schema(description = "New full link", example = "http://someAnotherLink")
    @Pattern(regexp = "((http:\\/\\/)|(https:\\/\\/)).*")
    @NotNull
    private String newFullUrl;

    @Schema(description = "New iterations count", example = "10")
    private Integer newIterations
;}
