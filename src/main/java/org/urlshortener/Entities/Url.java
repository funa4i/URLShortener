package org.urlshortener.Entities;


import com.fasterxml.jackson.annotation.JsonGetter;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "urls")
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
    @JoinColumn(name = "usermail", referencedColumnName = "mail")
    private User userMail;

    @JsonGetter(value = "userMail")
    private String getTheUserMail(){
        return userMail.getMail();
    }

    public void decreaseIterations(){
        iterations--;
    }
}
