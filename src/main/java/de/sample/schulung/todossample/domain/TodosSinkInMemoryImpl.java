package de.sample.schulung.todossample.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

//@Component
public class TodosSinkInMemoryImpl implements TodosSink {

    private final Map<Long, Todo> todos = new TreeMap<>();

    @Override
    public long count() {
        return todos.size();
    }

    @Override
    public Collection<Todo> findAll() {
        return Collections.unmodifiableCollection(todos.values());
    }

    @Override
    public Optional<Todo> findById(long id) {
        return Optional.ofNullable(todos.get(id));
    }

    @Override
    public boolean exists(long id) {
        return todos.containsKey(id);
    }

    @Override
    public void update(Todo todo) {
        if(todo.getId() == null) {
            Long newId = todos.keySet().stream()
              .max(Comparator.naturalOrder())
              .orElse(0L)
              + 1L;
            todo.setId(newId);
        }
        todos.put(todo.getId(), todo);
    }

    @Override
    public void delete(long id) {
        todos.remove(id);
    }
}
