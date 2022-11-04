package de.sample.schulung.todossample.boundary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public @Data class TodoDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @NotNull
    @Size(min = 3)
    private String title;
    private String description;
    private LocalDate dueDate;
    @Pattern(regexp = "n|p|c|a|cc")
    private String status;

}
