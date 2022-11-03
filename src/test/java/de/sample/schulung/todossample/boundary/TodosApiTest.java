package de.sample.schulung.todossample.boundary;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.sample.schulung.todossample.domain.NotFoundException;
import de.sample.schulung.todossample.domain.TodosService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@WebMvcTest
public class TodosApiTest {

    // TodosService mocken
    @MockBean
    TodosService service;
    @MockBean
    TodoDtoMapper mapper;

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

}
