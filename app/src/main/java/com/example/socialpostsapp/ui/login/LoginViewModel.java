package com.example.socialpostsapp.ui.login;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.socialpostsapp.ui.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class LoginViewModel extends ViewModel {

    MutableLiveData<Boolean> isLoading = new MutableLiveData();

    public void login(Context context, String email, String password){
        isLoading.setValue(true);
        Task<AuthResult> res = LoginClient.login(email, password);
        res.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    System.out.println(task.getResult().getUser().getUid());
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }else{
                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
                isLoading.setValue(false);
            }
        });
    }

}
