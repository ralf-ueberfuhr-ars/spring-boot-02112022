package de.sample.schulung.todossample.boundary;

import de.sample.schulung.todossample.domain.Todo;
import de.sample.schulung.todossample.domain.TodosService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TodosControllerTest {

    // Abhängigkeiten mocken
    @Mock
    TodosService service;
    @Mock
    TodoDtoMapper mapper;
    // Class-under-Test
    @InjectMocks
    TodosController controller;

    /*
     * Test für findAll():
     *
     *  - Arrange:
     *      service hat 1 Todo
     *      (mapper)
     *  - Act
     *      controller.findAll()
     *  - Assert
     *      Ergebnis enthält 1 TodoDto
     */
    @Test
    void shouldReturnSingleDtoWhenServiceHasSingleTodo() {
        // Arrange: Instruieren der Mocks mit Mockito
        Todo todo = new Todo();
        todo.setId(1L);
        todo.setTitle("test");
        when(service.findAll()).thenReturn(List.of(todo));
        TodoDto dto = new TodoDto();
        dto.setId(1L);
        dto.setTitle("test");
        when(mapper.map(todo)).thenReturn(dto);
        // Act
        Collection<TodoDto> result = controller.findAll();
        // Assert mit AssertJ und Mockito
        assertThat(result)
          .hasSize(1)
          .first().isSameAs(dto);
    }

    // Test2: wenn service.findAll() = leere Liste -> Ergebnis leer && Mapper nicht aufgerufen

}
