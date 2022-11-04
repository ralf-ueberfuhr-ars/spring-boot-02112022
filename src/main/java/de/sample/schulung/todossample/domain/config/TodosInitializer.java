package de.sample.schulung.todossample.domain.config;

import de.sample.schulung.todossample.domain.Todo;
import de.sample.schulung.todossample.domain.TodosService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TodosInitializer {

    private final TodosService service;
    private final ApplicationConfiguration config;

    @EventListener(ContextRefreshedEvent.class)
    public void initData() {
        if(config.isInitializeSampleDataOnStartup() && service.count()<1) {
            Todo todo = new Todo();
            todo.setTitle("Spring lernen");
            service.create(todo);
        }
    }

}
