package com.example.cinema.cinema_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HallService {
    @Autowired
    private HallRepository hallRepository;

    public List<Hall> findAll() {
        return hallRepository.findAll();
    }

    public Hall findById(int id) {
        return hallRepository.findById(id).orElse(null);
    }

    public Hall save(Hall hall) {
        return hallRepository.save(hall);
    }

    public void delete(int id) {hallRepository.deleteById(id);}

}