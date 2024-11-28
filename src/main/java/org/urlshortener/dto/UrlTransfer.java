package org.urlshortener.dto;


import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlTransfer {

    @Schema(name = "url", example = "http://someurl")
    @NotEmpty
    @Pattern(regexp = "((http:\\/\\/)|(https:\\/\\/)).*")
    private String url;


    @Schema(name = "iterations", example = "10")
    @Min(1)
    @NotNull
    private Integer iterations;

    @Null
    @Hidden
    private String mail;
}