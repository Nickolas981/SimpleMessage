package com.example.nickolas.simplemessage;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Stack;

import android.util.Base64;

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


//    public static String encode(String s, String key) {
//        return base64Encode(xorWithKey(s.getBytes(), key.getBytes()));
//    }
//
//    private static byte[] xorWithKey(byte[] a, byte[] key) {
//        byte[] out = new byte[a.length];
//        for (int i = 0; i < a.length; i++) {
//            out[i] = (byte) (a[i] ^ key[i%key.length]);
//        }
//        return out;
//    }
//
//    private static String base64Encode(byte[] bytes) {
//        String res = Base64.encodeToString(bytes,Base64.DEFAULT);
//        res = res.replaceAll("\\s", "");
//        return res;
//    }

    public static String generateDialogID(String a, String b){
        if (a.compareTo(b) > 0){
            return a+b;
        }else{
            return b+a;
        }
    }

}
