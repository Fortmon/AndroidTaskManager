package ru.university.ifmo.also.taskmanager.model;

public class Role {
    private int value;
    private String title;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Role(){

    }

    public Role(String title, int role){
        this.title = title;
        this.value = role;
    }
}
