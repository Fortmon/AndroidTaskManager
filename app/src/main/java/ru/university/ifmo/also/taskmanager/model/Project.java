package ru.university.ifmo.also.taskmanager.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Project {
    private UUID id;
    private String title;
    private String description;
    private UUID ownerId;
    private UserInfo owner;
    private Date creationDate;
    private List<UserInfo> users;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public UserInfo getOwner() {
        return owner;
    }

    public void setOwner(UserInfo owner) {
        this.owner = owner;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public List<UserInfo> getUsers() {
        return users;
    }
}
