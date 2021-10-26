package com.example.socialpostsapp.ui.main;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;

public class BGService extends Service {

    private static final String TAG = "BGService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long l) {
                Log.e(TAG, "onTick: " + l / 1000 );
            }

            @Override
            public void onFinish() {
                Log.e(TAG, "Finished: ");
            }
        };
        return START_STICKY;
    }
}
