package org.urlshortener.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.urlshortener.enums.Roles;

@Data
public class RoleChangeRequest {

    @NotNull
    private Roles newRole;

}
