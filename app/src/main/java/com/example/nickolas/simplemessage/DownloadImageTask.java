package com.example.nickolas.simplemessage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;

//class DownloadImageTask extends AsyncTask<Uri, Void, Bitmap> {
//    ImageView bmImage;
//
//    private static HashMap<URI, Bitmap> photoCash = new HashMap<>();
//    private static StorageReference mRef;
//    private Context context;
//
//    public DownloadImageTask(ImageView bmImage, Context context) {
//        this.bmImage = bmImage;
//        this.context = context;
//    }
//
//
//    public void downloadAvatar(String Uid) {
//        if (mRef == null) {
//            mRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://simplemessage-abdee.appspot.com/avatars");
//        }
//        ;
//
//        mRef.child(Uid + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                ex(uri);
//            }
//        });
//    }
//
//
//    private void ex(Uri uri) {
//        this.execute(uri);
//    }
//
//    @Override
//    protected void onPreExecute() {
//        // TODO Auto-generated method stub
//        super.onPreExecute();
//    }
//
//    protected Bitmap doInBackground(Uri... uris) {
//        Uri uridisplay = uris[0];
//        Bitmap mIcon11 = null;
//
//        if (photoCash.containsKey(uridisplay)) {
//            mIcon11 = photoCash.get(uridisplay);
//        } else {
//            Glide.with(context)
//                    .load(mRef)
//                    .into(bmImage);
//        }
//        return mIcon11;
//    }
//
//    @Override
//    protected void onPostExecute(Bitmap result) {
//        super.onPostExecute(result);
//        bmImage.setImageBitmap(result);
//    }
//}
class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    private static HashMap<String, Bitmap> photoCash = new HashMap<>();
    private static StorageReference mRef;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }


    public void downloadAvatar(String Uid){
        if (mRef == null){
            mRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://simplemessage-abdee.appspot.com/avatars");
        }
        mRef.child(Uid + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                ex(uri.toString());
            }
        });
    }

    private void ex(String url){
        this.execute(url);
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;

        if (photoCash.containsKey(urldisplay)){
            mIcon11 = photoCash.get(urldisplay);
        } else {
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
                photoCash.put(urldisplay, mIcon11);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
        }
        return mIcon11;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        bmImage.setImageBitmap(result);
    }
}
