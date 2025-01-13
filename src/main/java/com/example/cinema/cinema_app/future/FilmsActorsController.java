package com.example.cinema.cinema_app.future;

import com.example.cinema.cinema_app.FilmsActors;
import com.example.cinema.cinema_app.FilmsActorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/films-actors")
public class FilmsActorsController {

    @Autowired
    private FilmsActorsService filmsActorsService;

    @GetMapping
    public List<FilmsActors> getAllFilmsActors() {
        return filmsActorsService.getAllFilmsActors();
    }

    @GetMapping("/{actorId}/{filmId}")
    public ResponseEntity<FilmsActors> getFilmsActorsById(@PathVariable Long actorId, @PathVariable Long filmId) {
        return filmsActorsService.getFilmsActorsById(actorId, filmId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public FilmsActors createFilmsActors(@RequestBody FilmsActors filmsActors) {
        return filmsActorsService.saveFilmsActors(filmsActors);
    }

    @DeleteMapping("/{actorId}/{filmId}")
    public ResponseEntity<Void> deleteFilmsActors(@PathVariable Long actorId, @PathVariable Long filmId) {
        filmsActorsService.deleteFilmsActors(actorId, filmId);
        return ResponseEntity.noContent().build();
    }
}


