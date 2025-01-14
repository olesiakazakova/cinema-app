package com.example.cinema.cinema_app;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity//этот класс является сущностью JPA, он будет отображаться на таблицу в базе данных.
@Table(name = "actors")
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)// значения этого поля будут автоматически генерироваться с использованием последовательности
    @SequenceGenerator(name = "actor_seq", sequenceName = "actor_sequence", allocationSize = 1)
    private Long actorId;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "actor", cascade = CascadeType.PERSIST)
    private List<FilmsActors> filmsActors;

    public Long getActorId() {
        return actorId;
    }

    public void setActorId(Long actorId) {
        this.actorId = actorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FilmsActors> getFilmsActors() {
        return filmsActors;
    }

    public void setFilmsActors(List<FilmsActors> filmsActors) {
        this.filmsActors = filmsActors;
    }
}

