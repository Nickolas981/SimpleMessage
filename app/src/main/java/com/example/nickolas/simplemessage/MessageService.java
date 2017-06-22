package com.example.nickolas.simplemessage;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MessageService extends IntentService implements DialogListModel.DialogListListner {

    Intent intent;

    public MessageService() {
        super("MessageService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        DialogListModel dialogs = new DialogListModel(this);
        this.intent = intent;
    }


    @Override
    public void add() {

    }

    @Override
    public void success() {

    }

    @Override
    public void changed(MessageModel messageModel, String name, String photo) {
        NotificationManager manager;
        Notification myNotication;
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, 0);

        Notification.Builder builder = new Notification.Builder(this);

        builder.setAutoCancel(true);
        builder.setTicker("new message");
        builder.setContentTitle(name);
        builder.setContentText(messageModel.getBody());
        builder.setSmallIcon(R.drawable.common_google_signin_btn_icon_dark);
        builder.setContentIntent(pendingIntent);
        builder.setOngoing(false);
        builder.setNumber(100);
        builder.setDefaults(Notification.DEFAULT_VIBRATE);
        builder.build();

        myNotication = builder.getNotification();
        manager.notify(11, myNotication);
    }

    @Override
    public void changedView(int i) {

    }
}
