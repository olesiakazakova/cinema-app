package com.example.cinema.cinema_app;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID ticketId;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private int row;
    private int seat;

    @Enumerated(EnumType.STRING)
    private DiscountType discount;

    public DiscountType getDiscount() {
        return discount;
    }

    public void setDiscount(DiscountType discount) {
        this.discount = discount;
    }


    // Геттер для ticketId
    public UUID getTicketId() {
        return ticketId;
    }

    // Сеттер для ticketId
    public void setTicketId(UUID ticketId) {
        this.ticketId = ticketId;
    }

    // Геттер для session
    public Session getSession() {
        return session;
    }

    // Сеттер для session
    public void setSession(Session session) {
        this.session = session;
    }

    // Геттер для user
    public User getUser() {
        return user;
    }

    // Сеттер для user
    public void setUser(User user) {
        this.user = user;
    }

    // Геттер для row
    public int getRow() {
        return row;
    }

    // Сеттер для row
    public void setRow(int row) {
        this.row = row;
    }

    // Геттер для seat
    public int getSeat() {
        return seat;
    }

    // Сеттер для seat
    public void setSeat(int seat) {
        this.seat = seat;
    }

//    // Геттер для discount
//    public DiscountType getDiscount() {
//        return discount;
//    }
//
//    // Сеттер для discount
//    public void setDiscount(DiscountType discount) {
//        this.discount = discount;
//    }

    public UUID getSessionId() {
        return session != null ? session.getSessionId() : null;
    }

}
