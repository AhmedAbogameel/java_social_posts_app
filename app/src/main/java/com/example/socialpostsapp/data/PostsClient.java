package com.example.socialpostsapp.data;

import com.example.socialpostsapp.pojo.PostModel;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

public class PostsClient {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private static PostsClient INSTANCE;
    private final PostsInterface postsInterface;

    public PostsClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
                GsonConverterFactory.create()
        ).build();
        postsInterface = retrofit.create(PostsInterface.class);
    }

    public static PostsClient getINSTANCE() {
        if(INSTANCE == null){
            INSTANCE = new PostsClient();
        }
        return INSTANCE;
    }

    public Call<List<PostModel>> getPosts() {
        return postsInterface.getPosts();
    }

}
