package org.urlshortener.dto;


import lombok.Data;
import org.urlshortener.enums.Roles;

@Data
public class RoleChangeRequest {

    private Roles newRole;

}
