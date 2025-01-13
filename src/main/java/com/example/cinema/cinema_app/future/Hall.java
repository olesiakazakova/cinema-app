package com.example.cinema.cinema_app;

import jakarta.persistence.*;

@Entity
@Table(name = "halls")
@IdClass(HallId.class)
public class Hall {
    @Id
    @ManyToOne
    @JoinColumn(name = "cinema_id", nullable = false)
    private Cinema cinema;

    @Id
    private Short hallNumber;

    @Column(nullable = false)
    private Integer capacity;

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public Short getHallNumber() {
        return hallNumber;
    }

    public void setHallNumber(Short hallNumber) {
        this.hallNumber = hallNumber;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}



