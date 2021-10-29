package com.example.socialpostsapp.ui.splash;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.socialpostsapp.R;
import com.example.socialpostsapp.ui.login.LoginActivity;
import com.example.socialpostsapp.ui.main.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent;
                if(FirebaseAuth.getInstance().getCurrentUser() == null){
                    intent = new Intent(getApplicationContext(), LoginActivity.class);
                }else{
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }, 3000);
    }
}