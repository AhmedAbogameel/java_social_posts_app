package com.example.socialpostsapp.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.socialpostsapp.data.PostsClient;
import com.example.socialpostsapp.pojo.PostModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class PostsViewModel extends ViewModel {

    MutableLiveData<List<PostModel>> posts = new MutableLiveData<>();

    public void getPosts(){
         PostsClient.getINSTANCE().getPosts().enqueue(new Callback<List<PostModel>>() {
             @Override
             public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                 posts.setValue(response.body());
             }

             @Override
             public void onFailure(Call<List<PostModel>> call, Throwable t) {
                 System.out.println(t.toString());
             }
         });
    }

    public void addPost(String title, String body){
        PostsClient.getINSTANCE().addPost(title,body).enqueue(new Callback<PostModel>() {
            @Override
            public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                PostModel postModel = response.body();
                List<PostModel> oldPostsList = posts.getValue();
                oldPostsList.add(0, postModel);
                posts.setValue(oldPostsList);
            }

            @Override
            public void onFailure(Call<PostModel> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
    }

}
