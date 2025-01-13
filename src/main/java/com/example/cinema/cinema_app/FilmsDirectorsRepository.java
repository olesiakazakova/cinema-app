package com.example.cinema.cinema_app;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FilmsDirectorsRepository extends JpaRepository<FilmsDirectors, FilmsDirectorsId> {
    Optional<FilmsDirectors> findByDirector_DirectorIdAndFilm_FilmId(Long directorId, Long filmId);
}







