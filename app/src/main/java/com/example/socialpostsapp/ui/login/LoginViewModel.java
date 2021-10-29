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

/*

     initOTP();
    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(otp, "123456");
    signInWithPhoneAuthCredential(phoneAuthCredential);


    String otp;

    public void initOTP(){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+201553953843",
                60,
                TimeUnit.SECONDS,
                this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        otp = s;
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            System.out.println(user.getProviderId());
                        } else {
                            Toast.makeText(getApplicationContext(), "Sign In Failed!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    - FirebaseStorage
 */
