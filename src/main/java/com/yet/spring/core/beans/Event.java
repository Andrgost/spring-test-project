package com.yet.spring.core.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

@Component("event")
@Scope("prototype")
public class Event {

    private final int id = new Random().nextInt();
    @Autowired
    private String message;
    private final Date date;
    private final DateFormat df;

    @Autowired
    public Event(Date date, DateFormat df, String message) {
        this.date = date;
        this.df = df;
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Bean
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return String.format("id = %s, message = %s, date = %s", id, this.getMessage(), df.format(date));
    }
}
