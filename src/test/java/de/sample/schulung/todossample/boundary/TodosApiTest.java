package de.sample.schulung.todossample.boundary;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.sample.schulung.todossample.domain.NotFoundException;
import de.sample.schulung.todossample.domain.Todo;
import de.sample.schulung.todossample.domain.TodosService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@WebMvcTest
@ComponentScan(basePackageClasses = TodosController.class)
class TodosApiTest {

    // TodosService mocken
    @MockBean
    TodosService service;

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper jsonMapper;

    /*
     * Test1: wenn DELETE /todos/5 -> 204
     */

    @Test
    void shouldReturn204WhenDelete() throws Exception {
        mvc.perform(
            delete("/todos/5")
          )
          .andExpect(status().isNoContent());
    }

    /*
     * Test2: wenn DELETE /todos/5 && Service wirft NFE -> 404
     */

    @Test
    void shouldReturn404WhenDeleteNonExisting() throws Exception {
        doThrow(new NotFoundException(5L))
          .when(service).delete(5L);
        mvc.perform(
            delete("/todos/5")
          )
          .andExpect(status().isNotFound());
    }

    /*
     * Test3: wenn POST /todos && invalides DTO -> 422
     */

    @Test
    void shouldReturn422WhenCreateInvalidTodo() throws Exception {
        TodoDto dto = new TodoDto();
        dto.setTitle("t");
        String json = jsonMapper.writeValueAsString(dto);
        mvc.perform(
            post("/todos")
              .contentType(MediaType.APPLICATION_JSON)
              .content(json)
          )
          .andExpect(status().isUnprocessableEntity());
        verify(service, never()).create(any());
    }

    /*
     * Test: due_date im JSON
     */
    @Test
    void shouldUseSnakeCaseForDueDate() throws Exception {
        // Suche Todo nach ID
        Todo todo = new Todo();
        todo.setId(1L);
        todo.setTitle("test");
        todo.setDueDate(LocalDate.now());
        when(service.findById(1)).thenReturn(Optional.of(todo));
        mvc.perform(
            get("/todos/1")
          )
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$.due_date").exists())
        ;
    }

}
