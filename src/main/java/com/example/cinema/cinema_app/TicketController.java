package com.example.cinema.cinema_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private SessionService sessionService;

    @GetMapping
    public String getAllTickets(Model model) {
        List<Ticket> tickets = ticketRepository.findAll();
        tickets.forEach(ticket -> {
            System.out.println("Ticket ID: " + ticket.getTicketId());
            System.out.println("Session: " + ticket.getSession());
            System.out.println("User: " + ticket.getUser());
        });
        List<Session> cinemaSessions = sessionRepository.findAll();
        model.addAttribute("cinemaSessions", cinemaSessions);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        List<String> formattedStartTimes = cinemaSessions.stream()
                .map(session -> {
                    return session.getStartTime() != null ? session.getStartTime().format(timeFormatter) : "Неизвестно";
                })
                .collect(Collectors.toList());

        model.addAttribute("formattedStartTimes", formattedStartTimes);
        model.addAttribute("tickets", tickets);
        return "session/listTickets";
    }


    @GetMapping("/add")
    public String showAddTicketForm(Model model) {

        model.addAttribute("ticket", new Ticket());

        List<Session> cinemaSessions = sessionRepository.findAll();
        model.addAttribute("cinemaSessions", cinemaSessions);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        List<String> formattedStartTimes = cinemaSessions.stream()
                .map(session -> {
                    return session.getStartTime() != null ? session.getStartTime().format(timeFormatter) : "Неизвестно";
                })
                .collect(Collectors.toList());

        model.addAttribute("formattedStartTimes", formattedStartTimes);

        model.addAttribute("users", userRepository.findAll());

        model.addAttribute("discountTypes", DiscountType.values());

        return "session/addTicket";
    }

    @PostMapping("/add")
    public String addTicket(@ModelAttribute Ticket ticket) {
        System.out.println("Received ticket: " + ticket);

        // Проверка на наличие сессии
        Session session = ticket.getSession(); // Получаем объект Session из Ticket
        if (session == null || session.getSessionId() == null) {
            throw new IllegalArgumentException("Session must not be null");
        }

        // Проверяем, существует ли сессия в базе данных
        session = sessionRepository.findById(session.getSessionId()).orElse(null);
        if (session == null) {
            throw new IllegalArgumentException("Session not found");
        }

        ticket.setSession(session);
        ticketRepository.save(ticket);

        return "redirect:/tickets";
    }

    @PostMapping("/delete")
    public String deleteTicket(@RequestParam UUID ticketId) {
        try {
            ticketRepository.deleteById(ticketId);
            return "redirect:/tickets";
        } catch (Exception e) {
            return "redirect:/tickets?error=Ticket not found";
        }
    }

    @GetMapping("/edit")
    public String showEditTicketForm(@RequestParam UUID ticketId, Model model) {
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
        if (ticket == null) {
            return "redirect:/tickets?error=Ticket not found";
        }

        model.addAttribute("ticket", ticket);

        List<Session> cinemaSessions = sessionRepository.findAll();
        model.addAttribute("cinemaSessions", cinemaSessions);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        List<String> formattedStartTimes = cinemaSessions.stream()
                .map(session -> {
                    return session.getStartTime() != null ? session.getStartTime().format(timeFormatter) : "Неизвестно";
                })
                .collect(Collectors.toList());

        model.addAttribute("formattedStartTimes", formattedStartTimes);

        model.addAttribute("users", userRepository.findAll());

        return "session/editTicket"; // Здесь должна быть ваша HTML-страница для редактирования билета
    }

    @PostMapping("/edit")
    public String editTicket(@ModelAttribute Ticket ticket) {
        System.out.println("Updating ticket: " + ticket);

        // Проверка на наличие сессии
        Session session = ticket.getSession(); // Получаем объект Session из Ticket
        if (session == null || session.getSessionId() == null) {
            throw new IllegalArgumentException("Session must not be null");
        }

        // Проверяем, существует ли сессия в базе данных
        session = sessionRepository.findById(session.getSessionId()).orElse(null);
        if (session == null) {
            throw new IllegalArgumentException("Session not found");
        }

        // Устанавливаем сессию в билет
        ticket.setSession(session);
        ticketRepository.save(ticket);

        return "redirect:/tickets";
    }

}

