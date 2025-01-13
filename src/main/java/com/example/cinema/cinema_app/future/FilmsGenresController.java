package com.example.cinema.cinema_app.future;

import com.example.cinema.cinema_app.Film;
import com.example.cinema.cinema_app.FilmsGenres;
import com.example.cinema.cinema_app.FilmsGenresService;
import com.example.cinema.cinema_app.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/films-genres")
public class FilmsGenresController {
    @Autowired
    private FilmsGenresService filmsGenresService;

    @PostMapping
    public FilmsGenres createFilmsGenres(@RequestBody FilmsGenres filmsGenres) {
        return filmsGenresService.createFilmsGenres(filmsGenres);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteFilmsGenres(@RequestParam Long genreId, @RequestParam Long filmId) {
        Genre genre = new Genre();
        genre.setGenreId(genreId); // or use a service to find this entity
        Film film = new Film();
        film.setFilmId(filmId); // or use a service to find this entity
        boolean deleted = filmsGenresService.deleteFilmsGenres(genre, film);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}