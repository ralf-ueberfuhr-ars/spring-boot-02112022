package de.sample.schulung.todossample.boundary;

import de.sample.schulung.todossample.domain.NotFoundException;
import de.sample.schulung.todossample.domain.Todo;
import de.sample.schulung.todossample.domain.TodosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodosController {

    private final TodosService service;
    private final TodoDtoMapper mapper;

    /*
     * CREATE
     *
     *  - REQ:  POST /todos + JSON im Body
     *  - RESP:
     *   - 201 + Location-Header (+JSON im Body)
     *   - 422 (Validierungsfehler)
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@Valid @RequestBody TodoDto todo) {
        final Todo newTodo = service.create(mapper.map(todo));
        // Location: http://localhost:9080/todos/100
        final URI location = linkTo(methodOn(TodosController.class).findById(newTodo.getId()))
          .toUri();
        return ResponseEntity.created(location).build();
    }

    /*
     * READ - ALL
     *
     *  - REQ:  GET /todos
     *  - RESP:
     *   - 200 + JSON im Body [{...}, {...}, ...]
     *
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<TodoDto> findAll() {
        return service.findAll()
          .stream()
          .map(mapper::map)
          .collect(Collectors.toList());
    }

    /*
     * READ - SINGLE
     *
     *  - REQ:  GET /todos/{id}
     *  - RESP:
     *   - 200 + JSON im Body {...}
     *   - 404 (Not Found)
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TodoDto findById(@PathVariable("id") long id) {
        return service.findById(id)
          .map(mapper::map)
          .orElseThrow(() -> new NotFoundException(id));
    }

    /*
     * UPDATE (REPLACE)
     *
     *  - REQ:  PUT /todos/{id} + JSON im Body
     *  - RESP:
     *   - 204 (kein Body)
     *   - 404 (ID nicht gefunden)
     *   - 422 (Validierungsfehler)
     */
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(
      @PathVariable("id")
      long id,
      @Valid
      @RequestBody
      TodoDto todo
    ) {
        todo.setId(id);
        service.update(mapper.map(todo));
    }

    /*
     * DELETE
     *
     *  - REQ:  DELETE /todos/{id}
     *   -RESP:
     *   - 204 (kein Body)
     *   - 404 (ID nicht gefunden)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id) {
        service.delete(id);
    }

}
