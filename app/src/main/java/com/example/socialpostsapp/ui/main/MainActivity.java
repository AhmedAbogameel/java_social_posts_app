package com.example.socialpostsapp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.socialpostsapp.R;
import com.example.socialpostsapp.firebase.FirebaseMessagingHelper;
import com.example.socialpostsapp.pojo.PostModel;
import com.example.socialpostsapp.sql.SQLHelper;
import com.example.socialpostsapp.ui.login.LoginActivity;
import com.example.socialpostsapp.ui.supportChat.SupportChatActivity;
import com.google.firebase.auth.FirebaseAuth;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    PostsViewModel postsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        getProfileImage();

        new SQLHelper(this);
        new FirebaseMessagingHelper(this);

        postsViewModel = ViewModelProviders.of(this).get(PostsViewModel.class);
        postsViewModel.getPosts();

        RecyclerView postsRecyclerView = findViewById(R.id.postsRecyclerView);

        final PostsAdapter adapter = new PostsAdapter(postsViewModel);

        postsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        postsRecyclerView.setAdapter(adapter);

        initObservers(adapter);

    }

    private void initObservers(PostsAdapter adapter){
        postsViewModel.posts.observe(this, new Observer<List<PostModel>>() {
            @Override
            public void onChanged(List<PostModel> postModels) {
                findViewById(R.id.postsRecyclerView).setVisibility(View.VISIBLE);
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                if(postsViewModel.isOffline){
                    findViewById(R.id.fab).setVisibility(View.GONE);
                    findViewById(R.id.chatFAB).setVisibility(View.GONE);
                }
                adapter.setList(postModels);
            }
        });

        findViewById(R.id.signOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void getProfileImage(){
        ImageView imageView = findViewById(R.id.homeProfileImage);
        String imageUrl = "https://firebasestorage.googleapis.com/v0/b/pongoo.appspot.com/o/" + FirebaseAuth.getInstance().getUid() + "?alt=media&token=abdd5c8e-29d8-4f66-b9ea-9910431a1c7b";
        Glide.with(this).load(imageUrl).into(imageView);
    }

    public void onFABClicked(View view) {
        AddPostDialog.showDialog(this, this.postsViewModel, null);
    }

    public void onChatFABClicked(View view) {
        Intent intent = new Intent(this, SupportChatActivity.class);
        startActivity(intent);
    }
}