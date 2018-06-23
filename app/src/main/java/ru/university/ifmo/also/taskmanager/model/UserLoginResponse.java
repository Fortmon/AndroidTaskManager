package ru.university.ifmo.also.taskmanager.model;

public class UserLoginResponse {
    private String access_token;

    public String getToken() {
        return access_token;
    }

    public void setToken(String token) {
        this.access_token = token;
    }
}
