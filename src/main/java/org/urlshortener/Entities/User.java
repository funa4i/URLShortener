package org.urlshortener.Entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
import org.hibernate.annotations.CreationTimestamp;
import org.urlshortener.Enums.Roles;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Table(name = "users")
@NoArgsConstructor
public class User  {
    public User(String mail){
        this.mail = mail;
    }

    @Id
    @JsonIgnore
    @Setter(value = AccessLevel.PRIVATE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mail", nullable = false, unique = true)
    private String mail;

    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Roles role;

    @Column(name = "maxlinkavail", nullable = false)
    private Integer maxLinkAvail;

    @Column(name = "createlinksleft", nullable = false)
    private Integer createLinksLeft;

    @CreationTimestamp
    @Column(name = "lastcreate", nullable = false)
    private LocalDateTime lastCreate;

    public void decreaseCreatedLinks(){
        createLinksLeft--;
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority(role.name()));
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return mail;
//    }
}
