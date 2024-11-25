package org.urlshortener.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Schema(description = "Jwt response")
public class JwtResponse {
    @Schema(description = "Jwt token")
    private final String token;
}
