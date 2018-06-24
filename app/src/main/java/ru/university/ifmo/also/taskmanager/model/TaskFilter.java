package ru.university.ifmo.also.taskmanager.model;

public class TaskFilter {
    private String title;
    private String projectId;
    private String ownerLogin;
    private String assignedLogin;
    private Integer priority;
    private Integer type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public String getAssignedLogin() {
        return assignedLogin;
    }

    public void setAssignedLogin(String assignedLogin) {
        this.assignedLogin = assignedLogin;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    // private String creationTime;
}
