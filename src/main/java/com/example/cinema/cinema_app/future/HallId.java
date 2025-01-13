package com.example.cinema.cinema_app;

import java.io.Serializable;

public class HallId implements Serializable {
    private Cinema cinema;
    private Short hallNumber;

    public HallId() {}

    public HallId(Cinema cinema, Short hallNumber) {
        this.cinema = cinema;
        this.hallNumber = hallNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HallId)) return false;
        HallId hallId = (HallId) o;
        return cinema.equals(hallId.cinema) && hallNumber.equals(hallId.hallNumber);
    }

    @Override
    public int hashCode() {
        int result = cinema.hashCode();
        result = 31 * result + hallNumber.hashCode();
        return result;
    }

    // Getters and Setters (если нужно)
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
}



