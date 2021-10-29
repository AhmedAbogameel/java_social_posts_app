package com.example.socialpostsapp.ui.signUp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.socialpostsapp.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailET, passwordET;
    private String email, password;
    private Button signUpButton;
    private SignUpViewModel signUpViewModel;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);

        emailET = findViewById(R.id.emailEditText);
        passwordET = findViewById(R.id.passwordEditText);
        signUpButton = findViewById(R.id.signUpButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateForm() && imageUri != null){
                    signUpViewModel.signUp(view.getContext() , email, password, imageUri);
                }
            }
        });

        signUpViewModel.isLoading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean value) {
                if(value){
                    signUpButton.setEnabled(false);
                    signUpButton.setText(R.string.loading);
                }else{
                    signUpButton.setEnabled(true);
                    signUpButton.setText(R.string.signUp);
                }
            }
        });

    }

    private boolean validateForm(){
        email = emailET.getText().toString();
        password = passwordET.getText().toString();
        if(email.isEmpty()){
            emailET.setError("Email can't be empty!");
            return false;
        }else if(password.isEmpty()){
            passwordET.setError("Password can't be empty!");
            return false;
        }
        final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        if(!matcher.find()){
            emailET.setError("Email Pattern is Invalid!");
            return false;
        }
        if(password.length() < 8){
            passwordET.setError("Password must be at least 8 digits!");
            return false;
        }
        return true;
    }

    public void onImageClicked(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            imageUri = data.getData();
            Bitmap bitmap = null;
            try{
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            }catch (Exception e){
                System.out.println(e);
            }
            ImageView imageView = findViewById(R.id.profilePicButton);
            if(bitmap != null){
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}