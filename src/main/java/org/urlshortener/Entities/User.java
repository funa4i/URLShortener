package org.urlshortener.Entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.urlshortener.Enums.Roles;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User implements UserDetails {
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<SimpleGrantedAuthority> resList = new ArrayList<>();
        resList.add(new SimpleGrantedAuthority(role.name()));
        if (role == Roles.GOD){
            resList.add(new SimpleGrantedAuthority(Roles.ADMIN.name()));
        }
        if (resList.contains(new SimpleGrantedAuthority(Roles.ADMIN.name()))){
            resList.add(new SimpleGrantedAuthority(Roles.USER.name()));
        }
       return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return mail;
    }

    @Override
    public boolean isAccountNonLocked(){
        return role != Roles.BUN;
    }
}
