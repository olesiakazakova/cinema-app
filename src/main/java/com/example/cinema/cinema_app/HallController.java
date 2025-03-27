package com.example.cinema.cinema_app;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/halls")
public class HallController {

    @Autowired
    private HallRepository hallRepository;

    @GetMapping()
    public String getAllHalls(Model model) {
        List<Hall> halls = hallRepository.findAll();
        model.addAttribute("halls", halls);
        return "session/listHalls";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("hall", new Hall());
        return "session/addHall";
    }

    @PostMapping("/add")
    public String addHall(@ModelAttribute Hall hall) {
        try {
            hallRepository.save(hall);
            return "redirect:/halls";
        } catch (Exception e) {
            return "film/error";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Hall hall = hallRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid hall Id:" + id));
        model.addAttribute("hall", hall);
        return "session/editHall";
    }

    @PostMapping("/edit/{id}")
    public String updateHall(@PathVariable int id, @ModelAttribute Hall hall) {
        hall.setHallId(id);
        try {
            hallRepository.save(hall);
            return "redirect:/halls";
        } catch (Exception e) {
            return "film/error";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteHall(@PathVariable int id) {
        try {
            hallRepository.deleteById(id);
            return "redirect:/halls";
        } catch (Exception e) {
            return "film/error";
        }
    }

}

