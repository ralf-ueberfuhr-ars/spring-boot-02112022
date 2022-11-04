package de.sample.schulung.todossample.domain;

import java.util.Collection;
import java.util.Optional;

public interface TodosSink {
    long count();

    Collection<Todo> findAll();

    Optional<Todo> findById(long id);

    boolean exists(long id);

    void update(Todo todo); // CREATE + UPDATE

    void delete(long id);
}
