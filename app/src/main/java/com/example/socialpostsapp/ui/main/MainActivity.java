package com.example.socialpostsapp.ui.main;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.socialpostsapp.R;
import com.example.socialpostsapp.pojo.PostModel;
import com.example.socialpostsapp.sql.SQLHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    PostsViewModel postsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new SQLHelper(this);

        postsViewModel = ViewModelProviders.of(this).get(PostsViewModel.class);
        postsViewModel.getPosts();

        RecyclerView postsRecyclerView = findViewById(R.id.postsRecyclerView);

        final PostsAdapter adapter = new PostsAdapter(postsViewModel);

        postsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        postsRecyclerView.setAdapter(adapter);

        postsViewModel.posts.observe(this, new Observer<List<PostModel>>() {
            @Override
            public void onChanged(List<PostModel> postModels) {
                findViewById(R.id.postsRecyclerView).setVisibility(View.VISIBLE);
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                adapter.setList(postModels);
            }
        });
    }

    public void onFABClicked(View view) {
        AddPostDialog.showDialog(this, this.postsViewModel, null);
    }
}