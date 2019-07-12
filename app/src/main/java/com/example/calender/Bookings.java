package com.example.calender;

public class Bookings {

    private String date;
    private int bookingsCount;

    public Bookings(String date, int bookingsCount) {
        this.date = date;
        this.bookingsCount = bookingsCount;
    }

    public String getDate() {
        return date;
    }

    public int getBookingsCount() {
        return bookingsCount;
    }
}
