package com.example.cinema.cinema_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HallService {

    private final HallRepository hallRepository;

    @Autowired
    public HallService(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    public List<Hall> getAllHalls() {
        return hallRepository.findAll();
    }

    public Optional<Hall> getHall(Short hallNumber) {
        return hallRepository.findById(hallNumber);
    }

    public Hall createHall(Hall hall) {
        return hallRepository.save(hall);
    }

    public Hall updateHall(Short hallNumber, Hall hallDetails) {
        Hall hall = hallRepository.findById(hallNumber)
                .orElseThrow(() -> new RuntimeException("Hall not found with id " + hallNumber));
        hall.setCinema(hallDetails.getCinema());
        hall.setCapacity(hallDetails.getCapacity());
        return hallRepository.save(hall);
    }

    public void deleteHall(Short hallNumber) {
        hallRepository.deleteById(hallNumber);
    }
}


