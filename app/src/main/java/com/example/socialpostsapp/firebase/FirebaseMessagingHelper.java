package com.example.socialpostsapp.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import com.example.socialpostsapp.R;
import com.example.socialpostsapp.ui.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingHelper extends FirebaseMessagingService {

    private static Context context;

    public FirebaseMessagingHelper(){

    }

    public FirebaseMessagingHelper(Context context){
        FirebaseMessagingHelper.context = context;
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        Task<String> task = FirebaseMessaging.getInstance().getToken();
        task.addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(task.isSuccessful()){
                    System.out.println("-=--=-=-=-=-=-=-=-=-=--=-=-=-=-=---==--=-=-");
                    System.out.println(task.getResult());
                    System.out.println("-=--=-=-=-=-=-=-=-=-=--=-=-=-=-=---==--=-=-");
                }
            }
        });
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        showLocalNotification(notification.getTitle(), notification.getBody());
    }

    private void showLocalNotification(String title, String body){
        int NOTIFICATION_ID = 1;
        String CHANNEL_ID = "0";
        CharSequence name = "BasicChannel";
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.enableVibration(true);
//            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//            AudioAttributes audioAttributes = new AudioAttributes.Builder().build();
//            mChannel.setSound(alarmSound, audioAttributes);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body);

//        Intent resultIntent = new Intent(context, MainActivity.class);
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//        stackBuilder.addParentStack(MainActivity.class);
//        stackBuilder.addNextIntent(resultIntent);
//        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(resultPendingIntent);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
