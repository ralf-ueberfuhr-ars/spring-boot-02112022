package de.sample.schulung.todossample;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

// Business-Logik
@Validated
@Service
@RequiredArgsConstructor
public class TodosService {

    private final ApplicationEventPublisher publisher;

    // In-Memory-Implementation
    // TODO later, replace it by a CRUD Repository (Persistence/Entity Layer)
    private final Map<Long, Todo> todos = new TreeMap<>();

    long count() {
        return todos.size();
    }

    /**
     * Gibt alle Todos zurück.
     *
     * @return eine unveränderliche Collection
     */
    public Collection<Todo> findAll() {
        return Collections.unmodifiableCollection(todos.values());
    }

    /**
     * Durchsucht die Todos nach einer ID.
     *
     * @param id die ID
     * @return das Suchergebnis
     */
    public Optional<Todo> findById(long id) {
        return Optional.ofNullable(todos.get(id));
    }

    /**
     * Fügt ein Item in den Datenbestand hinzu. Dabei wird eine ID generiert.
     *
     * @param item das anzulegende Item (ohne ID)
     * @return das gespeicherte Item (mit ID)
     */
    public Todo create(@Valid Todo item) {
        Long newId = todos.keySet().stream()
          .max(Comparator.naturalOrder())
          .orElse(0L)
          + 1L;
        // wird später wieder ersetzt
        final var result = new Todo();
        result.setId(newId);
        result.setTitle(item.getTitle());
        result.setDescription(item.getDescription());
        result.setDueDate(item.getDueDate());
        todos.put(newId, result);
        publisher.publishEvent(
          new TodosChangedEvent(result, TodosChangedEvent.ChangeType.CREATED)
        );
        return result;
    }

    /**
     * Aktualisiert ein Item im Datenbestand.
     *
     * @param item das zu ändernde Item mit ID
     * @throws NotFoundException wenn das Todo nicht vorhanden ist
     */
    public void update(@Valid Todo item) {
        // remove separat, um nicht neue Einträge hinzuzufügen (put allein würde auch ersetzen)
        if (null != todos.remove(item.getId())) {
            todos.put(item.getId(), item);
            publisher.publishEvent(new TodosChangedEvent(item, TodosChangedEvent.ChangeType.REPLACED));
        } else {
            throw new NotFoundException(item.getId());
        }
    }

    /**
     * Entfernt ein Item aus dem Datenbestand.
     *
     * @param id die ID des zu löschenden Items
     * @throws NotFoundException wenn das Todo nicht vorhanden ist
     */
    public void delete(long id) {
        Todo removedTodo = todos.remove(id);
        if (null != removedTodo) {
            publisher.publishEvent(new TodosChangedEvent(removedTodo, TodosChangedEvent.ChangeType.DELETED));
        } else {
           throw new NotFoundException(id);
        }

    }

}
