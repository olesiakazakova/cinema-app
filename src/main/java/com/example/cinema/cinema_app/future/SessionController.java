package com.example.cinema.cinema_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/sessions")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @GetMapping
    public String listSessions(Model model) {
        List<Session> sessions = sessionService.findAll();
        model.addAttribute("sessions", sessions);
        return "sessions/list"; // Thymeleaf view
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("session", new Session());
        return "sessions/add"; // Thymeleaf view
    }

    @PostMapping("/add")
    public String addSession(@Valid @ModelAttribute Session session, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "sessions/add";
        }
        sessionService.save(session);
        return "redirect:/sessions";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Session session = sessionService.findById(id);
        if (session == null) {
            return "redirect:/sessions";
        }
        model.addAttribute("session", session);
        return "sessions/edit"; // Thymeleaf view
    }

    @PostMapping("/edit/{id}")
    public String editSession(@PathVariable Long id, @Valid @ModelAttribute Session session, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "sessions/edit";
        }
        session.setSessionId(id);
        sessionService.save(session);
        return "redirect:/sessions";
    }

    @GetMapping("/delete/{id}")
    public String deleteSession(@PathVariable Long id) {
        sessionService.delete(id);
        return "redirect:/sessions";
    }
}



