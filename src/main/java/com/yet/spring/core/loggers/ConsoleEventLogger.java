package com.yet.spring.core.loggers;

import com.yet.spring.core.beans.Event;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("consoleEventLogger")
@Scope("singleton")
public class ConsoleEventLogger implements EventLogger {

    public void logEvent(Event event) {
        System.out.println(event);
    }
}