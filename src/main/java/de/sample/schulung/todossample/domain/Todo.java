package de.sample.schulung.todossample.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public @Data class Todo {

    private Long id;
    @NotNull
    @Size(min = 3)
    private String title;
    private String description;
    private LocalDate dueDate;
    private TodoStatus status = TodoStatus.NEW;

    public enum TodoStatus {
        NEW, PROGRESS, COMPLETED, ARCHIVED, CANCELED
    }

}
