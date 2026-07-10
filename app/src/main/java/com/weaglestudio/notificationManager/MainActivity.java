package com.weaglestudio.notificationManager;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        if(checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[] {Manifest.permission.POST_NOTIFICATIONS},100);
        }


        scheduleNotification();
    }

    public void scheduleNotification(){

        Intent intent =new Intent(this,NotificationReceiver.class);

        PendingIntent pendingIntent=PendingIntent.getBroadcast(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);

        long interval=60*1000;//1 minute

        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis(),
                interval,
                pendingIntent
        );
    }
}