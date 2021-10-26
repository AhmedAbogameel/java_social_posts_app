package com.example.socialpostsapp.pojo;

public class AddPostModel {
    private String title;
    private String body;
    private int userId;

    public AddPostModel(String title, String body, int userId) {
        this.title = title;
        this.body = body;
        this.userId = userId;
    }
}
