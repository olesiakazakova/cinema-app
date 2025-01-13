package com.example.cinema.cinema_app.future;

import com.example.cinema.cinema_app.FilmsDirectors;
import com.example.cinema.cinema_app.FilmsDirectorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/films-directors")
public class FilmsDirectorsController {
    @Autowired
    private FilmsDirectorsService filmsDirectorsService;

    @GetMapping
    public List<FilmsDirectors> getAllFilmsDirectors() {
        return filmsDirectorsService.getAllFilmsDirectors();
    }

    @GetMapping("/{directorId}/{filmId}")
    public ResponseEntity<FilmsDirectors> getFilmsDirectorsById(@PathVariable Long directorId, @PathVariable Long filmId) {
        return filmsDirectorsService.getFilmsDirectorsById(directorId, filmId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public FilmsDirectors saveFilmsDirectors(@RequestBody FilmsDirectors filmsDirectors) {
        return filmsDirectorsService.saveFilmsDirectors(filmsDirectors);
    }


    @DeleteMapping("/{directorId}/{filmId}")
    public ResponseEntity<Void> deleteFilmsDirectors(@PathVariable Long directorId, @PathVariable Long filmId) {
        if (filmsDirectorsService.deleteFilmsDirectors(directorId, filmId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}






