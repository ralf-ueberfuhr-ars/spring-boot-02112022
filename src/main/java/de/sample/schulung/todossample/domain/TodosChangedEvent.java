package de.sample.schulung.todossample.domain;

import lombok.Data;

public @Data class TodosChangedEvent {

    private final Todo todo;
    private final ChangeType type;

    public enum ChangeType {
        CREATED, REPLACED, DELETED
    }

}
