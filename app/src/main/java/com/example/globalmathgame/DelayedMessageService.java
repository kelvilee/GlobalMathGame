package com.example.globalmathgame;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class DelayedMessageService extends AppCompatActivity {
    public static final int NOTIFICATION_ID = 5453;
    public static final String NOTIFICATION_CHANNEL_ID = DelayedMessageService.class.getSimpleName();

    private void showText(final String text) {
        Intent actionIntent = new Intent(this, JavaMainActivity.class);

        PendingIntent actionPendingIntent = PendingIntent.getActivity(
                this,
                0,
                actionIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setContentTitle("Wow")
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[] {0, 1000})
                .setContentIntent(actionPendingIntent)
                .setAutoCancel(true);

        NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_ID+"_name",
                NotificationManager.IMPORTANCE_HIGH);

        NotificationManager notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notifManager.createNotificationChannel(channel);

        notifManager.notify(NOTIFICATION_ID, builder.build());
    }
}
