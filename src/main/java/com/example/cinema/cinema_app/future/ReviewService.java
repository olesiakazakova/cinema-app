package com.example.cinema.cinema_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    public Review findById(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }

    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }

    public List<Review> findByFilm(Film film) {
        return reviewRepository.findByFilm(film);
    }

    public List<Review> findByUser(User user) {
        return reviewRepository.findByUser(user);
    }
}


