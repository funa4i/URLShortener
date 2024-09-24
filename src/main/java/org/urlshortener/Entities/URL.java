package org.urlshortener.Entities;


import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "urls")
public class URL {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "shortURL")
    private String shortURL;

    @Column(name = "fullURL")
    private String fullURL;

    @Column(name = "iterations")
    private long iterations;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "userID")
    private User userID;
}
