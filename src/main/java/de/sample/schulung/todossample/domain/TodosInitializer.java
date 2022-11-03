package de.sample.schulung.todossample.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TodosInitializer {

    private final TodosService service;

    @EventListener(ContextRefreshedEvent.class)
    public void initData() {
        if(service.count()<1) {
            Todo todo = new Todo();
            todo.setTitle("Spring lernen");
            service.create(todo);
        }
    }

}
