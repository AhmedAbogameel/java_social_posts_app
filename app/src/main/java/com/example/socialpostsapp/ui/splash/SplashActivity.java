package com.example.socialpostsapp.ui.splash;

import android.content.Intent;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.socialpostsapp.R;
import com.example.socialpostsapp.ui.login.LoginActivity;
import com.example.socialpostsapp.ui.main.MainActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseApp.initializeApp(this);

        ImageView splashImage = findViewById(R.id.splashImage);

        splashImage.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                splashImage.clearAnimation();
                Intent intent;
                if(FirebaseAuth.getInstance().getCurrentUser() == null){
                    intent = new Intent(getApplicationContext(), LoginActivity.class);
                }else{
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }, 1500);
    }
}