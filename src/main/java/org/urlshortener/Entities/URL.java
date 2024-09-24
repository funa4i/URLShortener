package org.urlshortener.Entities;


import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "urls")
public class URL {

    @Id
    private long id;

    @Column(name = "shortURL", nullable = false)
    private String shortURL;

    @Column(name = "fullURL", nullable = false)
    private String fullURL;

    @Column(name = "iterations", nullable = false)
    private int iterations;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "userID")
    private User userID;
}
