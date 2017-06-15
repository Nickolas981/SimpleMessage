package com.example.nickolas.simplemessage;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


import static com.example.nickolas.simplemessage.Reversed.reversed;

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
    public static ArrayList<MessageModel> getReversedList(ArrayList<MessageModel> a){
        ArrayList<MessageModel> b = new ArrayList<>();

        for (MessageModel model: Reversed.reversed(a)){
            b.add(model);
        }
        return b;
    }
}
