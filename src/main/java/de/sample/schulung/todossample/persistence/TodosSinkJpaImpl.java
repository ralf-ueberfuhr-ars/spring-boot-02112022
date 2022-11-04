package de.sample.schulung.todossample.persistence;

import de.sample.schulung.todossample.domain.Todo;
import de.sample.schulung.todossample.domain.TodosSink;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TodosSinkJpaImpl implements TodosSink {

    private final TodosRepository repo;
    private final TodoEntityMapper mapper;

    @Override
    public long count() {
        return repo.count();
    }

    @Override
    public Collection<Todo> findAll() {
        return repo.findAll()
          .stream()
          .map(mapper::map)
          .collect(Collectors.toList());
    }

    @Override
    public Optional<Todo> findById(long id) {
        return repo.findById(id).map(mapper::map);
    }

    @Override
    public boolean exists(long id) {
        return repo.existsById(id);
    }

    @Override
    public void update(Todo todo) {
        TodoEntity entity = mapper.map(todo);
        TodoEntity savedEntity = repo.save(entity);
        todo.setId(savedEntity.getId());
    }

    @Override
    public void delete(long id) {
        repo.deleteById(id);
    }
}
