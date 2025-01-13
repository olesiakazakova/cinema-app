package com.example.cinema.cinema_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private FilmService filmService; // Добавляем FilmService для доступа к фильмам

    @GetMapping
    public String listReviews(Model model) {
        List<Review> reviews = reviewService.findAll();
        model.addAttribute("reviews", reviews);
        return "reviews/list"; // Thymeleaf view
    }

    @GetMapping("/add/{filmId}")
    public String showAddForm(@PathVariable Long filmId, Model model) {
        Review review = new Review();

        // Получаем объект Film по filmId из базы данных
        Film film = filmService.findById(filmId);
        if (film == null) {
            return "redirect:/films"; // Обработка ошибки, если фильм не найден
        }

        review.setFilm(film); // Устанавливаем Film в отзыв
        model.addAttribute("review", review);
        return "reviews/add"; // Thymeleaf view
    }

    @PostMapping("/add")
    public String addReview(@Valid @ModelAttribute Review review, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("Ошибки валидации: " + bindingResult.getAllErrors());
            return "reviews/add";
        }
        reviewService.save(review);
        return "redirect:/films"; // Или к списку отзывов
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Review review = reviewService.findById(id);
        if (review == null) {
            return "redirect:/reviews"; // Обработка ошибки, если отзыв не найден
        }
        model.addAttribute("review", review);
        return "reviews/edit"; // Thymeleaf view
    }

    @PostMapping("/edit/{id}")
    public String editReview(@PathVariable Long id, @Valid @ModelAttribute Review review, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("Ошибки валидации: " + bindingResult.getAllErrors());
            return "reviews/edit";
        }
        review.setReviewId(id);
        reviewService.save(review);
        return "redirect:/reviews";
    }

    @GetMapping("/delete/{id}")
    public String deleteReview(@PathVariable Long id) {
        reviewService.delete(id);
        return "redirect:/reviews";
    }
}




