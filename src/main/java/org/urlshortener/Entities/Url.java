package org.urlshortener.Entities;


import com.fasterxml.jackson.annotation.JsonGetter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "urls")
public class Url {

    @Id
    @Setter(AccessLevel.PRIVATE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shortURL", nullable = false)
    private String shortUrl;

    @Column(name = "fullURL", nullable = false)
    private String fullUrl;

    @Column(name = "iterations", nullable = false)
    private Integer iterations;



    @ManyToOne()
    @JoinColumn(name = "USERMAIL", referencedColumnName = "MAIL")
    private User userMail;

    @JsonGetter(value = "userMail")
    private String getTheUserMail(){
        return userMail.getMail();
    }

    public void decreaseIterations(){
        iterations--;
    }
}
