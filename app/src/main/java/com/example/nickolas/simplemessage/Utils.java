package com.example.nickolas.simplemessage;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.google.android.gms.tasks.OnSuccessListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.content.Context.DOWNLOAD_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Nickolas on 14.06.2017.
 */

public class Utils {
    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static ArrayList<MessageModel> getReversedList(ArrayList<MessageModel> a) {
        ArrayList<MessageModel> b = new ArrayList<>();

        for (MessageModel model : Reversed.reversed(a)) {
            b.add(model);
        }
        return b;
    }

    public static boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) MainActivity.activity.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static class sendNotification extends AsyncTask<String, Void, Bitmap> {

        Context ctx;
        String message, photo, name;
        URL url;

        public sendNotification(Context context) {
            super();
            this.ctx = context;
        }

        public void send(String name,String photo,String message){
            this.name = name;
            this.photo = photo;
            this.message = message;
            if (DownloadImageTask.photoCash.containsKey(this.photo)){
                fin(DownloadImageTask.photoCash.get(photo));
            }else{
                DownloadImageTask.mRef.child(photo + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        execute(uri.toString());
                    }
                });
            }
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            InputStream in;
            try {

                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                in = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(in);
                return myBitmap;


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private void fin(Bitmap bitmap){
            try {
                NotificationManager manager;
                Notification myNotication;
                manager = (NotificationManager) ctx.getSystemService(NOTIFICATION_SERVICE);
                Intent mIntent = new Intent(ctx, Dialog.class);
                mIntent.putExtra("id", photo);
                mIntent.putExtra("name", name);
                PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 1, mIntent, 0);

                Notification.Builder builder = new Notification.Builder(ctx);

                builder.setAutoCancel(true);
                builder.setTicker("New message");
                builder.setContentTitle(name);
                builder.setContentText(message);
                builder.setLargeIcon(bitmap);
                builder.setContentIntent(pendingIntent);
                builder.setOngoing(false);
                builder.setNumber(100);
                builder.setDefaults(Notification.DEFAULT_VIBRATE);
                builder.build();

                myNotication = builder.getNotification();
                manager.notify(11, myNotication);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            fin(result);
        }
    }

    public static String generateDialogID(String a, String b) {
        if (a.compareTo(b) > 0) {
            return a + b;
        } else {
            return b + a;
        }
    }

}
