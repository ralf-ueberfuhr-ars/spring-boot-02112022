package de.sample.schulung.todossample;

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

import java.net.URI;
import java.util.Collection;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/todos")
public class TodosController {

    /*
     * CREATE
     *
     *  - REQ:  POST /todos + JSON im Body
     *  - RESP:
     *   - 201 + Location-Header (+JSON im Body)
     *   - 422 (Validierungsfehler)
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody Todo todo) {
        // Pseudo ID
        todo.setId(100L);
        // Location: http://localhost:9080/todos/100
        final URI location = linkTo(methodOn(TodosController.class).findById(todo.getId()))
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
    public Collection<Todo> findAll() {
        Todo todo = new Todo();
        todo.setId(1L);
        todo.setTitle("Spring lernen");
        return List.of(todo);
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
    public Todo findById(@PathVariable("id") long id) {
        Todo todo = new Todo();
        todo.setId(id);
        todo.setTitle("Spring lernen");
        return todo;
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
      @RequestBody
      Todo todo) {
        // do nothing
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
        // do nothing
    }

}
