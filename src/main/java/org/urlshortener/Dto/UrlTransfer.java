package org.urlshortener.Dto;


import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlTransfer {

    @NotEmpty
    @Pattern(regexp = "((http:\\/\\/)|(https:\\/\\/)).*")
    private String url;

    @Min(1)
    @NotNull
    private Integer iterations;

    @Null
    private String mail;
}