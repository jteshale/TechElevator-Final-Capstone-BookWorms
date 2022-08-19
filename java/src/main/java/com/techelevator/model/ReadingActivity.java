package com.techelevator.model;

public class ReadingActivity {
    private int minutes_read;
    private int books_read;
    private int books_reading;

    public ReadingActivity(int minutes_read, int books_read, int books_reading) {
        this.minutes_read = minutes_read;
        this.books_read = books_read;
        this.books_reading = books_reading;
    }

    public int getMinutes_read() {
        return minutes_read;
    }

    public void setMinutes_read(int minutes_read) {
        this.minutes_read = minutes_read;
    }

    public int getBooks_read() {
        return books_read;
    }

    public void setBooks_read(int books_read) {
        this.books_read = books_read;
    }

    public int getBooks_reading() {
        return books_reading;
    }

    public void setBooks_reading(int books_reading) {
        this.books_reading = books_reading;
    }
}
