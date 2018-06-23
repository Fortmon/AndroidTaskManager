package ru.university.ifmo.also.taskmanager.model;

import java.util.UUID;

public class CreateTask {
    private String title;
    private String description;
    private UUID projectId;
    private UserInfo assignedUser;
    private int priority;
    private int type;

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

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    public UserInfo getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(UserInfo assignedUser) {
        this.assignedUser = assignedUser;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
