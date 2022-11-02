package de.sample.schulung.todossample;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

public @Data class Todo {

    private Long id;
    private String title;
    private String description;
    @JsonProperty("due_date")
    private LocalDate dueDate;

}