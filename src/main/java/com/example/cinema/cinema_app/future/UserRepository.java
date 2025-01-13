package com.example.cinema.cinema_app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // Можно добавить методы, такие как поиск пользователя по email
    User findByEmail(String email);
}


