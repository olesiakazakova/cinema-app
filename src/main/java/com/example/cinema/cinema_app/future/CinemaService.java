package com.example.cinema.cinema_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CinemaService {

    @Autowired
    private CinemaRepository cinemaRepository;

    public List<Cinema> getAllCinemas() {
        return cinemaRepository.findAll();
    }

    public Optional<Cinema> getCinemaById(Long id) {
        return cinemaRepository.findById(id);
    }

    public Cinema saveCinema(Cinema cinema) {
        return cinemaRepository.save(cinema);
    }

    public void deleteCinema(Long id) {
        cinemaRepository.deleteById(id);
    }
}




