package de.sample.schulung.todossample.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

// Business-Logik
@Validated
@Service
@RequiredArgsConstructor
public class TodosService {

    private final ApplicationEventPublisher publisher;
    private final TodosSink sink;

    long count() {
        return sink.count();

    }

    /**
     * Gibt alle Todos zurück.
     *
     * @return eine unveränderliche Collection
     */
    public Collection<Todo> findAll() {
        return sink.findAll();
    }

    /**
     * Durchsucht die Todos nach einer ID.
     *
     * @param id die ID
     * @return das Suchergebnis
     */
    public Optional<Todo> findById(long id) {
        return sink.findById(id);
    }

    /**
     * Fügt ein Item in den Datenbestand hinzu. Dabei wird eine ID generiert.
     *
     * @param item das anzulegende Item (ohne ID)
     * @return das gespeicherte Item (mit ID)
     */
    public Todo create(@Valid Todo item) {
        item.setId(null);
        sink.update(item);
        publisher.publishEvent(
          new TodosChangedEvent(item, TodosChangedEvent.ChangeType.CREATED)
        );
        return item;
    }

    /**
     * Aktualisiert ein Item im Datenbestand.
     *
     * @param item das zu ändernde Item mit ID
     * @throws NotFoundException wenn das Todo nicht vorhanden ist
     */
    public void update(@Valid Todo item) {
        // remove separat, um nicht neue Einträge hinzuzufügen (put allein würde auch ersetzen)
        if (sink.exists(item.getId())) {
            sink.update(item);
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
        if (sink.exists(id)) {
            Todo todo = sink.findById(id).orElse(null);
            sink.delete(id);
            publisher.publishEvent(new TodosChangedEvent(todo, TodosChangedEvent.ChangeType.DELETED));
        } else {
           throw new NotFoundException(id);
        }

    }

}
