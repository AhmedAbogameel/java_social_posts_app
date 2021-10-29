package com.example.socialpostsapp.ui.signUp;

import android.net.Uri;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

public class SignUpClient {

    public static Task<AuthResult> signUp(String email, String password){
        return FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password);
    }

    public static UploadTask uploadImage(Uri imageUri){
        String path = "gs://pongoo.appspot.com/";
        return FirebaseStorage.getInstance(path).getReference(String.valueOf(Uri.parse(FirebaseAuth.getInstance().getUid()))).putFile(imageUri);
    }

}
