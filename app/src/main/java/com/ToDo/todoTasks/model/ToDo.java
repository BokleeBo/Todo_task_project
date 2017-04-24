package com.ToDo.todoTasks.model;


public class ToDo {

    private int id;
    private String userID;
    private String title;
    private String content;

    public ToDo(String content, int id, String title, String userID) {
        this.content = content;
        this.id = id;
        this.title = title;
        this.userID = userID;
    }

    public ToDo(String content, String title, String userID) {
        this.content = content;
        this.title = title;
        this.userID = userID;
    }

    public ToDo() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }


}
