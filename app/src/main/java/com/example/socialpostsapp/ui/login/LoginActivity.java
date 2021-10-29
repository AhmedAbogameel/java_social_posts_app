package com.example.socialpostsapp.ui.login;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.socialpostsapp.R;
import com.example.socialpostsapp.ui.main.MainActivity;
import com.example.socialpostsapp.ui.signUp.SignUpActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private EditText emailET, passwordET;
    private Button loginButton;
    private String email, password;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

    }

    private void init(){
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        emailET = findViewById(R.id.emailEditText);
        passwordET = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateForm()){
                    loginViewModel.login(view.getContext(), email, password);
                }
            }
        });

        loginViewModel.isLoading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean value) {
                if(value){
                    loginButton.setEnabled(false);
                    loginButton.setText(R.string.loading);
                }else{
                    loginButton.setEnabled(true);
                    loginButton.setText(R.string.login);
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

    public void onRegisterButtonClicked(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}