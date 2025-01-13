package com.example.cinema.cinema_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public Ticket findById(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }

    public void save(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    public void delete(Long id) {
        ticketRepository.deleteById(id);
    }
}


