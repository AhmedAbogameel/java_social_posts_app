package com.example.socialpostsapp.ui.supportChat;

import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.socialpostsapp.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupportChatActivity extends AppCompatActivity {

    private ChatRecyclerAdapter adapter;
    private ChatViewModel chatViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_chat);

        initRecyclerView();
        initViewModel();
        initSendFAB();

    }

    private void initViewModel(){
        chatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
        chatViewModel.getMessages();
        chatViewModel.messages.observe(this, new Observer<List<Map<String, Object>>>() {
            @Override
            public void onChanged(List<Map<String, Object>> maps) {
                adapter.setList(maps);
                chatViewModel.listenForMessages(adapter);
            }
        });
    }

    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        adapter = new ChatRecyclerAdapter(linearLayoutManager);
        RecyclerView chatRecyclerView = findViewById(R.id.chatRecyclerView);
        linearLayoutManager.setReverseLayout(true);
        chatRecyclerView.setLayoutManager(linearLayoutManager);
        chatRecyclerView.setAdapter(adapter);
    }

    private void initSendFAB(){
        EditText editText = findViewById(R.id.chatEditText);
        findViewById(R.id.sendMessageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString();
                if(!text.isEmpty()){
                    chatViewModel.sendMessage(text);
                    editText.setText("");
                }
            }
        });
    }

}