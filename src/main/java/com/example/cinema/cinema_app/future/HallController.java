package com.example.cinema.cinema_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/halls")
public class HallController {

    private final HallService hallService;

    @Autowired
    public HallController(HallService hallService) {
        this.hallService = hallService;
    }

    @GetMapping
    public List<Hall> getAllHalls() {
        return hallService.getAllHalls();
    }

    @GetMapping("/{hallNumber}")
    public ResponseEntity<Hall> getHall(@PathVariable Short hallNumber) {
        return hallService.getHall(hallNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Hall createHall(@RequestBody Hall hall) {
        return hallService.createHall(hall);
    }

    @PutMapping("/{hallNumber}")
    public ResponseEntity<Hall> updateHall(@PathVariable Short hallNumber, @RequestBody Hall hallDetails) {
        Hall updatedHall = hallService.updateHall(hallNumber, hallDetails);
        return ResponseEntity.ok(updatedHall);
    }

    @DeleteMapping("/{hallNumber}")
    public ResponseEntity<Void> deleteHall(@PathVariable Short hallNumber) {
        hallService.deleteHall(hallNumber);
        return ResponseEntity.noContent().build();
    }
}


