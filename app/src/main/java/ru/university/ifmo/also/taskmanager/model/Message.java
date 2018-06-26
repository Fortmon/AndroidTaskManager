package ru.university.ifmo.also.taskmanager.model;

import java.util.UUID;

public class Message {
    private UUID Id;
    public UserInfo FromUser;
    public ProjectInfo Project;
    public int Code;
    public int Role;
}
