package com.example.socialpostsapp.ui.login;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginClient {

    public static Task<AuthResult> login(String email, String password){
        return FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password);
    }

}
