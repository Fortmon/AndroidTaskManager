package ru.university.ifmo.also.taskmanager.model;

public class UserLoginRequest {
    private String username;
    private String password;

    public UserLoginRequest(String username, String password){
        this.username = username;
        this.password = password;
    }
}
