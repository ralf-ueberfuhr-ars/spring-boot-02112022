package de.sample.schulung.todossample.persistence;

import de.sample.schulung.todossample.domain.Todo;
import org.springframework.stereotype.Component;

@Component
public class TodoEntityMapper {

    public Todo map(TodoEntity source) {
        if (null == source)
            return null;
        Todo result = new Todo();
        result.setId(source.getId());
        result.setTitle(source.getTitle());
        result.setDescription(source.getDescription());
        result.setDueDate(source.getDueDate());
        result.setStatus(source.getStatus());
        return result;
    }

    public TodoEntity map(Todo source) {
        if (null == source)
            return null;
        TodoEntity result = new TodoEntity();
        result.setId(source.getId());
        result.setTitle(source.getTitle());
        result.setDescription(source.getDescription());
        result.setDueDate(source.getDueDate());
        result.setStatus(source.getStatus());
        return result;
    }

}
