package de.sample.schulung.todossample.persistence;

import de.sample.schulung.todossample.domain.Todo;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "todos")
public @Data class TodoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Size(min = 3)
    private String title;
    private String description;
    @Column(name = "DUEDATE")
    private LocalDate dueDate;
    @Enumerated
    private Todo.TodoStatus status = Todo.TodoStatus.NEW;

}
