package com.example.socialpostsapp.data;

import com.example.socialpostsapp.pojo.PostModel;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface PostsInterface {

    @GET("posts")
    public Call<List<PostModel>> getPosts();


}
