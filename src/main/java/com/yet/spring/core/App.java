package com.yet.spring.core;

import com.yet.spring.core.beans.Client;
import com.yet.spring.core.beans.Event;
import com.yet.spring.core.beans.EventType;
import com.yet.spring.core.loggers.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

public class App {

    private static ConfigurableApplicationContext ctx;
    private Client client;
    private Event event;
    private Map<EventType, EventLogger> loggers;

    public static void main(String[] args) {
        ctx = new ClassPathXmlApplicationContext("spring.xml");

        App app = (App) ctx.getBean("app");

        app.logEvent("Some event for user 1 ", EventType.INFO);
        app.logEvent("Some event for user 2 ", EventType.ERROR);
        app.logEvent("Some event for user 3 ", null);

        ctx.close();
    }

    public App() {}

    public App(Client client) {
        super();
        this.client = client;
    }

    public App(Client client, Map<EventType, EventLogger> loggers) {
        super();
        this.client = client;
        this.loggers = loggers;
    }

    public void logEvent(String msg, EventType eventType) {
        String message = msg.replaceAll(client.getId(), client.getFullName());
        message += client.getGreeting();

        event = ctx.getBean(Event.class);
        event.setMessage(message);

        if (eventType == EventType.INFO) {
            //Console log
            ConsoleEventLogger consoleEventLogger = (ConsoleEventLogger) ctx.getBean("consoleEventLogger");
            consoleEventLogger.logEvent(event);
        } else if (eventType == EventType.ERROR) {
            CombinedEventLogger combinedEventLogger = (CombinedEventLogger) ctx.getBean("combinedEventLogger");
            combinedEventLogger.logEvent(event);
        } else {
            //Cache file event
            CacheFileEventLogger cacheFileEventLogger = (CacheFileEventLogger) ctx.getBean("cacheFileEventLogger");
            cacheFileEventLogger.logEvent(event);
            cacheFileEventLogger.logEvent(event);
            cacheFileEventLogger.logEvent(event);
            cacheFileEventLogger.logEvent(event);
            cacheFileEventLogger.logEvent(event);
            cacheFileEventLogger.logEvent(event);
        }
    }
}