package de.sample.schulung.todossample;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public @Data class Todo {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @NotNull
    @Size(min = 3)
    private String title;
    private String description;
    @JsonProperty("due_date")
    private LocalDate dueDate;

}
