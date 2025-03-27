package com.example.cinema.cinema_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SessionService {
    @Autowired
    private SessionRepository sessionRepository;

    public List<Session> findAll() {
        return sessionRepository.findAll();
    }

    public Session findById(UUID id) {
        return sessionRepository.findById(id).orElse(null);
    }

    public Session save(Session session) {
        return sessionRepository.save(session);
    }

    public void delete(UUID id) {sessionRepository.deleteById(id);}

}