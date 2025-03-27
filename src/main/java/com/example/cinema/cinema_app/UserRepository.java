package com.example.cinema.cinema_app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // Дополнительные методы для поиска можно добавить здесь
    User findByEmail(String email);
    void deleteByEmail(String email);
}
