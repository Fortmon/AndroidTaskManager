package ru.university.ifmo.also.taskmanager.model;

import java.util.Date;
import java.util.UUID;

public class ProjectInfo {
    private UUID id;
    private String title;
    private String description;
    private UserInfo owner;
    private String creationDate;
    private int countTask;

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserInfo getOwner() {
        return owner;
    }

    public String  getCreationDate() {
        return creationDate;
    }

    public int getCountTask() {
        return countTask;
    }
}
