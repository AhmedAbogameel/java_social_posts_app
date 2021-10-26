package com.example.socialpostsapp.pojo;

public class EditPostModel {
    private String title;
    private String body;
    private int userId;
    private int id;

    public EditPostModel(String title, String body, int userId) {
        this.title = title;
        this.body = body;
        this.userId = userId;
    }

    public EditPostModel(PostModel postModel){
        this.title = postModel.getTitle();
        this.body = postModel.getBody();
        this.userId = postModel.getUserId();
        this.id = postModel.getId();
    }
}
