package com.yet.spring.core;

import com.yet.spring.core.beans.Client;
import com.yet.spring.core.beans.Event;
import com.yet.spring.core.beans.EventType;
import com.yet.spring.core.context.AppConfiguration;
import com.yet.spring.core.loggers.CacheFileEventLogger;
import com.yet.spring.core.loggers.CombinedEventLogger;
import com.yet.spring.core.loggers.ConsoleEventLogger;
import com.yet.spring.core.loggers.EventLogger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("app")
@Scope("singleton")
public class App {

    private static ConfigurableApplicationContext ctx;
    private Client client;
    private Event event;
    private Map<EventType, EventLogger> loggers;

    public static void main(String[] args) {
        ctx = new AnnotationConfigApplicationContext(AppConfiguration.class);

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