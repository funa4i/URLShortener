package org.urlshortener.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.urlshortener.enums.Roles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;


@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User implements UserDetails {
    public User(String mail){
        this.mail = mail;
    }

    @Id
    @Setter(value = AccessLevel.PRIVATE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @Column(name = "mail", nullable = false, unique = true)
    private String mail;

    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @JsonIgnore
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
       return resList;
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
    public boolean isAccountNonExpired() {
        return true;
    }


    @Override
    public boolean isAccountNonLocked(){
        return role != Roles.BAN;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
