package ru.university.ifmo.also.taskmanager.model;

import java.util.Date;
import java.util.UUID;

public class UserInfo {
    private UUID id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private boolean isApproved;
    private Date creationDate;
    private Date lastLoginDate;

    public UUID getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }
}
