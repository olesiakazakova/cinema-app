package com.example.cinema.cinema_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping
    public String listTickets(Model model) {
        List<Ticket> tickets = ticketService.findAll();
        model.addAttribute("tickets", tickets);
        return "tickets/list"; // Thymeleaf view
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("ticket", new Ticket());
        return "tickets/add"; // Thymeleaf view
    }

    @PostMapping("/add")
    public String addTicket(@Valid @ModelAttribute Ticket ticket, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "tickets/add";
        }
        ticketService.save(ticket);
        return "redirect:/tickets";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Ticket ticket = ticketService.findById(id);
        if (ticket == null) {
            return "redirect:/tickets";
        }
        model.addAttribute("ticket", ticket);
        return "tickets/edit"; // Thymeleaf view
    }

    @PostMapping("/edit/{id}")
    public String editTicket(@PathVariable Long id, @Valid @ModelAttribute Ticket ticket, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "tickets/edit";
        }
        ticket.setTicketId(id);
        ticketService.save(ticket);
        return "redirect:/tickets";
    }

    @GetMapping("/delete/{id}")
    public String deleteTicket(@PathVariable Long id) {
        ticketService.delete(id);
        return "redirect:/tickets";
    }
}


