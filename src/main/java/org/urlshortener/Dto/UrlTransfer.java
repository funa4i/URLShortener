package org.urlshortener.Dto;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UrlTransfer {

    @NotEmpty
    @Pattern(regexp = "((http:\\/\\/)|(https:\\/\\/)).*")
    private String url;

    @NotEmpty
    @Min(1)
    private Integer iterations;

    @Null
    @Setter
    private String mail;
}