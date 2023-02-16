package com.yet.spring.core.beans;

import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

public class Event {

    private final int id = new Random().nextInt();
    private String message;
    private final Date date;
    private final DateFormat df;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Event(Date date, DateFormat df) {
        this.date = date;
        this.df = df;
    }

    @Override
    public String toString() {
        return String.format("id = %s, message = %s, date = %s", id, this.getMessage(), df.format(date));
    }
}
