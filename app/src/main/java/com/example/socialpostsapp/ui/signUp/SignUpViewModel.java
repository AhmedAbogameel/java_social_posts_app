package com.example.socialpostsapp.ui.signUp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.socialpostsapp.ui.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.storage.UploadTask;

public class SignUpViewModel extends ViewModel {

    MutableLiveData<Boolean> isLoading = new MutableLiveData();

    public void signUp(Context context, String email, String password, Uri imageUri){
        isLoading.setValue(true);

        Task<AuthResult> res = SignUpClient.signUp(email, password);

        res.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    UploadTask uploadTask = SignUpClient.uploadImage(imageUri);
                    uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful()){

                                Intent intent = new Intent(context, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent);

                                isLoading.setValue(false);
                            }
                        }
                    });
                }else{
                    isLoading.setValue(false);
                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
