package de.sample.schulung.todossample.boundary;

import de.sample.schulung.todossample.domain.Todo;
import org.springframework.stereotype.Component;

// TODO: Use MapStruct
@Component
public class TodoDtoMapper {

    public Todo map(TodoDto source) {
        if (null == source)
            return null;
        Todo result = new Todo();
        result.setId(source.getId());
        result.setTitle(source.getTitle());
        result.setDescription(source.getDescription());
        result.setDueDate(source.getDueDate());
        String statusAsString = source.getStatus();
        if (statusAsString != null) {
            Todo.TodoStatus status;
            switch (statusAsString) {
            case "n":
                status = Todo.TodoStatus.NEW;
                break;
            case "p":
                status = Todo.TodoStatus.PROGRESS;
                break;
            case "a":
                status = Todo.TodoStatus.ARCHIVED;
                break;
            case "cc":
                status = Todo.TodoStatus.CANCELED;
                break;
            case "c":
                status = Todo.TodoStatus.COMPLETED;
                break;
            default:
                throw new UnsupportedOperationException();
            }
            result.setStatus(status);
        }
        return result;
    }

    public TodoDto map(Todo source) {
        if (null == source)
            return null;
        TodoDto result = new TodoDto();
        result.setId(source.getId());
        result.setTitle(source.getTitle());
        result.setDescription(source.getDescription());
        result.setDueDate(source.getDueDate());
        Todo.TodoStatus status = source.getStatus();
        if (status != null) {
            String statusAsString;
            switch (status) {
            case NEW:
                statusAsString = "n";
                break;
            case PROGRESS:
                statusAsString = "p";
                break;
            case ARCHIVED:
                statusAsString = "a";
                break;
            case CANCELED:
                statusAsString = "cc";
                break;
            case COMPLETED:
                statusAsString = "c";
                break;
            default:
                throw new UnsupportedOperationException();
            }
            result.setStatus(statusAsString);
        }
        return result;
    }

}
