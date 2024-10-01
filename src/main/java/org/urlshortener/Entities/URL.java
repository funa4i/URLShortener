package org.urlshortener.Entities;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "urls")
public class URL {

    @Id
    @Setter(AccessLevel.PRIVATE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shortURL", nullable = false)
    private String shortURL;

    @Column(name = "fullURL", nullable = false)
    private String fullURL;

    @Column(name = "iterations", nullable = false)
    private Integer iterations;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "userID")
    private User userID;
}
