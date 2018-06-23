package ru.university.ifmo.also.taskmanager.model;

import java.util.List;

public class ValidateModel<T> {
    private T entity;
    private List<Error> errors;

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }
}
