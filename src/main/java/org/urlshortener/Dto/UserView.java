package org.urlshortener.Dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Component;
import org.urlshortener.Enums.Roles;

import java.time.LocalDateTime;


@Data
@Component
public class UserView {

    private String mail;

    private Roles role;

    private Integer maxLinkAvail;

    private Integer createLinksLeft;

    private LocalDateTime lastCreate;
}
