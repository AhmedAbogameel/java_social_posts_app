package com.example.socialpostsapp.ui.main;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import com.example.socialpostsapp.R;
import com.example.socialpostsapp.pojo.PostModel;

public class AddPostDialog {

    public static void showDialog(Context context, PostsViewModel postsViewModel, @Nullable PostModel postModel) {
        AlertDialog alertDialog;

        final EditText titleInput = new EditText(context);
        titleInput.setHint("Title");

        final EditText bodyInput = new EditText(context);
        bodyInput.setHint("Body");

        if(postModel != null){
            titleInput.setText(postModel.getTitle());
            bodyInput.setText(postModel.getBody());
        }

        final LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(titleInput, 0);
        layout.addView(bodyInput, 1);
        layout.setPadding(20,20,20,20);

        final Button button = new Button(context);
        layout.addView(button, 2);
        button.setText(R.string.submit);

        alertDialog = new AlertDialog.Builder(context).create();

        button.setOnClickListener(view -> {
            String title = titleInput.getText().toString();
            String body = bodyInput.getText().toString();
            if(title.equals("") || body.equals("")){
                return;
            }
            layout.removeViewAt(2);
            if(postModel == null){
                postsViewModel.addPost(title, body);
            }else{
                postModel.setTitle(title);
                postModel.setBody(body);
                postsViewModel.editPost(postModel);
            }
            alertDialog.dismiss();
        });

        alertDialog.setTitle(postModel == null ? "Add Post" : "Edit Post");
        alertDialog.setView(layout);
        alertDialog.show();
    }

}
