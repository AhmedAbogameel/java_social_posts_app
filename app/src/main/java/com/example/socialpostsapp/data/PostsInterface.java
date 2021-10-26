package com.example.socialpostsapp.data;

import com.example.socialpostsapp.pojo.AddPostModel;
import com.example.socialpostsapp.pojo.PostModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface PostsInterface {

    @GET("posts")
    public Call<List<PostModel>> getPosts();

    @POST("posts")
    public Call<PostModel> addPost(@Body AddPostModel addPostModel);

}
