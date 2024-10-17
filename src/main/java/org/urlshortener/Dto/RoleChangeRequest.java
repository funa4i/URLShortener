package org.urlshortener.Dto;


import lombok.Data;
import org.urlshortener.Enums.Roles;

@Data
public class RoleChangeRequest {

    private Roles newRole;

}
