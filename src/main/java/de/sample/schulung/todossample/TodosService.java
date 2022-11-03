package de.sample.schulung.todossample;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

// Business-Logik
@Service
public class TodosService {

    // In-Memory-Implementation
    // TODO later, replace it by a CRUD Repository (Persistence/Entity Layer)
    private final Map<Long, Todo> todos = new TreeMap<>();

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
     * @throws IllegalArgumentException wenn die ID bereits belegt ist
     */
    public Todo create(Todo item) {
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
        return result;
    }

    /**
     * Aktualisiert ein Item im Datenbestand.
     *
     * @param item das zu ändernde Item mit ID
     * @return <tt>true</tt>, wenn das Item gefunden und geändert wurde
     * @throws IllegalArgumentException wenn die ID nicht belegt ist
     */
    public boolean update(Todo item) {
        // remove separat, um nicht neue Einträge hinzuzufügen (put allein würde auch ersetzen)
        return null != todos.remove(item.getId()) && null == todos.put(item.getId(), item);
    }

    /**
     * Entfernt ein Item aus dem Datenbestand.
     *
     * @param id die ID des zu löschenden Items
     * @return <tt>true</tt>, wenn das Item gefunden und gelöscht wurde
     */
    public boolean delete(long id) {
        return null != todos.remove(id);
    }

}
