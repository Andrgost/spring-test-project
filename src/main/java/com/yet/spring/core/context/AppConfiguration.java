package com.yet.spring.core.context;

import com.yet.spring.core.App;
import com.yet.spring.core.beans.Client;
import com.yet.spring.core.beans.Event;
import com.yet.spring.core.beans.EventType;
import com.yet.spring.core.loggers.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@ComponentScan(basePackages = "com.yet.spring.core")
@Configuration
public class AppConfiguration {
    private final Properties properties = new Properties();
    private final Map<EventType, EventLogger> loggers = new HashMap<>();

    @Bean(name = "app")
    public App app() {

        loggers.put(EventType.INFO, new ConsoleEventLogger());

        ArrayList<EventLogger> loggersList = new ArrayList<>();
        loggersList.add(new ConsoleEventLogger());
        loggersList.add(new FileEventLogger("fileName_1"));

        loggers.put(EventType.ERROR, new CombinedEventLogger(loggersList));
        return new App(client(), loggers);
    }

    @Bean(name = "client")
    public Client client() {
        return new Client(getStringProperty("id"), getStringProperty("name"));
    }

    @Bean(name = "event")
    public Event event() {
        return new Event(new java.util.Date(), java.text.DateFormat.getDateTimeInstance(), "test");
    }

    @Bean(name = "cacheFileEventLogger")
    public CacheFileEventLogger cacheFileEventLogger() {
        return new CacheFileEventLogger("fileName_1", 5);
    }

    private String getStringProperty(String propertyKey) {
        return properties.getProperty(propertyKey);
    }
}
