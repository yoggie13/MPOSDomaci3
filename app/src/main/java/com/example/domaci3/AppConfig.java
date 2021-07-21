package com.example.domaci3;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;

public class AppConfig extends Application {
    public static final String CHANNEL1 = "channel1";
    public static final String CHANNEL2 = "channel2";

    @Override
    public void onCreate() {
        super.onCreate();
        createChannel();
    }

    public void createChannel(){
        NotificationChannel channel1 = null;
        NotificationChannel channel2 = null;

        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel1 = new NotificationChannel(CHANNEL1, "channel1", NotificationManager.IMPORTANCE_HIGH);
            channel1.enableLights(true);
            channel1.setLightColor(Color.RED);
            channel1.enableVibration(true);
            channel1.setDescription("Dobre notifikacije");
            channel1.setShowBadge(true);

            channel2= new NotificationChannel(CHANNEL2, "channel2", NotificationManager.IMPORTANCE_HIGH);
            channel2.enableLights(true);
            channel2.setLightColor(Color.GREEN);
            channel2.enableVibration(true);
            channel2.setDescription("Lose notifikacije");
            channel2.setShowBadge(true);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }
    }
}
