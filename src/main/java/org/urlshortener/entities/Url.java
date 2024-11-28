package org.urlshortener.entities;


import com.fasterxml.jackson.annotation.JsonGetter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "url")
public class Url {

    @Id
    @Setter(AccessLevel.PRIVATE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shorturl", nullable = false)
    private String shortUrl;

    @Column(name = "fullurl", nullable = false)
    private String fullUrl;

    @Column(name = "iterations", nullable = false)
    private Integer iterations;

    @Column(name = "validuntil", nullable = false)
    private LocalDateTime validUntil;

    @ManyToOne()
    @JoinColumn(name = "userid", referencedColumnName = "id")
    private User user;

    @JsonGetter(value = "usermail")
    private String getTheUserMail(){
        return user.getMail();
    }

    public void decreaseIterations(){
        iterations--;
    }
}
