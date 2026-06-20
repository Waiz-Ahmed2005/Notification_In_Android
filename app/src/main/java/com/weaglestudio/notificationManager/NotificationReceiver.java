package com.weaglestudio.notificationManager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NotificationReceiver extends BroadcastReceiver {

    private static final String Channel_ID="ReminderChannel";
    @Override
    public void onReceive(Context context, Intent intent) {
        createChannel(context);

        String currentTime=new SimpleDateFormat(
                "HH:mm:ss",
                Locale.getDefault()).format(new Date());

        Intent openIntent= new Intent(context, MainActivity.class);

        PendingIntent openPendingIntent=PendingIntent.getActivity(
                context,
                0,
                openIntent,
                PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder builder=new NotificationCompat.Builder(
                context,Channel_ID
        )
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("SCHEDULED REMINDER")
                .setContentText("CURRENT TIME:"+currentTime)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Dynamic Notif Generated at: "+currentTime))
                .addAction(android.R.drawable.ic_menu_view,
                        "open",
                        openPendingIntent
                        )
                .setAutoCancel(true)
                ;

        NotificationManager manager=(NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE
        );

        manager.notify((int)System.currentTimeMillis(),builder.build());

    }

    private void createChannel(Context context){


        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){

            NotificationChannel channel=new NotificationChannel(
                    Channel_ID,
                    "Reminder_Notifications",
                    NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager=context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

}
