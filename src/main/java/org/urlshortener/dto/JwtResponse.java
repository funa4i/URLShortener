package org.urlshortener.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Schema(description = "Jwt response")
public class JwtResponse {

    @NotNull
    @Schema(description = "Jwt token")
    private final String token;
}
