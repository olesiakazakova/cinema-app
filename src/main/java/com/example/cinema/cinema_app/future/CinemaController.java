package com.example.cinema.cinema_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cinemas")
public class CinemaController {

    @Autowired
    private CinemaService cinemaService;

    @GetMapping
    public List<Cinema> getAllCinemas() {
        return cinemaService.getAllCinemas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cinema> getCinemaById(@PathVariable Long id) {
        Optional<Cinema> cinema = cinemaService.getCinemaById(id);
        return cinema.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Cinema createCinema(@RequestBody Cinema cinema) {
        return cinemaService.saveCinema(cinema);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cinema> updateCinema(@PathVariable Long id, @RequestBody Cinema cinemaDetails) {
        Optional<Cinema> optionalCinema = cinemaService.getCinemaById(id);
        if (optionalCinema.isPresent()) {
            Cinema cinema = optionalCinema.get();
            cinema.setName(cinemaDetails.getName());
            cinema.setAddress(cinemaDetails.getAddress());
            cinema.setPhone(cinemaDetails.getPhone());
            return ResponseEntity.ok(cinemaService.saveCinema(cinema));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCinema(@PathVariable Long id) {
        cinemaService.deleteCinema(id);
        return ResponseEntity.noContent().build();
    }
}


