package com.example.socialpostsapp.ui.signUp;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpClient {

    public static Task<AuthResult> signUp(String email, String password){
        return FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password);
    }

}
