package com.example.nickolas.simplemessage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;
import java.util.HashMap;
import static com.example.nickolas.simplemessage.MainActivity.idToken;
class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    private static HashMap<String, Bitmap> photoCash = new HashMap<>();
    private static StorageReference mRef;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    public void downloadAvatar(){
        if (mRef == null){
            mRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://simplemessage-abdee.appspot.com/avatars");
        }
        this.execute(mRef.child(idToken).getDownloadUrl().toString());
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
