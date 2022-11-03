package de.sample.schulung.todossample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TodosChangedEventLogger {

    @EventListener(TodosChangedEvent.class)
    public void logEvent(TodosChangedEvent event) {
        log.info(event.toString());
    }

}
