package com.municipality.jdeidetmarjeyoun.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.municipality.jdeidetmarjeyoun.MainActivity;
import com.municipality.jdeidetmarjeyoun.R;
import com.municipality.jdeidetmarjeyoun.death.Deaths_Activity;
import com.municipality.jdeidetmarjeyoun.event.Events_Activity;
import com.municipality.jdeidetmarjeyoun.news.News_Activity;
import com.municipality.jdeidetmarjeyoun.notifications.Notifications_fragment;

/**
 * Created by najielchemaly on 12/18/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        Intent intent = new Intent(this, MainActivity.class);

        if(remoteMessage.getData() != null && remoteMessage.getData().get("type") != null) {
            String type = remoteMessage.getData().get("type");
            if("latestnews".equals(type.toLowerCase())) {
                intent = new Intent(this, News_Activity.class);
            } else if("activities".equals(type.toLowerCase())) {
                intent = new Intent(this, Events_Activity.class);
            } else if("sociallife".equals(type.toLowerCase())) {
                intent = new Intent(this, Deaths_Activity.class);
            } else {
                intent = new Intent(this, Notifications_fragment.class);
            }
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); // default sound of the notification
        notificationBuilder
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.logo)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(remoteMessage.getNotification().getBody()));

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        notificationManager.notify(0, notificationBuilder.build());
    }
}
