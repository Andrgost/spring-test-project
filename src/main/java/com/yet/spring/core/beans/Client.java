package com.yet.spring.core.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("client")
@Scope("singleton")
public class Client {

    @Autowired
    @Value("$.id")
    private String id;
    @Autowired
    @Value("$.name")
    private String fullName;
    @Autowired
    @Value("$.greeting")
    private String greeting;

    public String getId() {
        return id;
    }

    @Autowired
    public Client(@Qualifier("clientId") String id, @Qualifier("clientFullName") String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    @Autowired
    @Qualifier("clientId")
    @Value("$.id")
    public void setId(String id) {
        this.id = id;
    }

    public String getGreeting() {
        return greeting;
    }

    @Autowired
    @Qualifier("clientFullName")
    @Value("$.name")
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Autowired
    @Value("$.greeting")
    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }
}
