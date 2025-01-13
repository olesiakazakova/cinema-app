package com.example.cinema.cinema_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    public List<Session> findAll() {
        return sessionRepository.findAll();
    }

    public Session findById(Long id) {
        return sessionRepository.findById(id).orElse(null);
    }

    public void save(Session session) {
        sessionRepository.save(session);
    }

    public void delete(Long id) {
        sessionRepository.deleteById(id);
    }
}


