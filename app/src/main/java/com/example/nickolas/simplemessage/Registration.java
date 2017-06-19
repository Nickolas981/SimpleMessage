package com.example.nickolas.simplemessage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.r0adkll.slidr.Slidr;

import java.io.IOException;

public class Registration extends AppCompatActivity implements View.OnClickListener{

    ImageView photo;
    EditText name,email, pass;
    Button submit, cancel;
    private final int GALLERY_REQUEST = 1;
    static Uri URIphoto;
    private static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        super.onCreate(savedInstanceState);
        Slidr.attach(this);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().setTitle(getResources().getString(R.string.registration));

        photo = (ImageView) findViewById(R.id.registration_photo);

        name = (EditText) findViewById(R.id.registration_name);
        email = (EditText) findViewById(R.id.registration_email);
        pass = (EditText) findViewById(R.id.registration_pass);

        submit = (Button) findViewById(R.id.registration_submit);
        cancel = (Button) findViewById(R.id.registration_cancel);

        Intent intent = getIntent();
        email.setText(intent.getStringExtra("email"));
        pass.setText(intent.getStringExtra("pass"));

        photo.setOnClickListener(this);
        submit.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = new Intent(this, MainActivity.class);

        switch (id){
            case R.id.registration_cancel:
                setResult(RESULT_CANCELED, intent);
                finish();
                break;
            case R.id.registration_submit:
                if (checkFields()){
                    registrate();
                }else if (URIphoto == null){
                    Toast.makeText(this, "Please upload photo", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "Error, some fields are wrong!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.registration_photo:
                setPhoto();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && data != null){
            URIphoto = data.getData();
            Bitmap bitmap  = BitmapFactory.decodeResource(getResources(), R.drawable.common_full_open_on_phone);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), URIphoto);
            } catch (IOException e) {
                e.printStackTrace();
            }
            float aspectRatio = bitmap.getWidth() /
                    (float) bitmap.getHeight();
            int width = 720;
            int height = Math.round(width / aspectRatio);

            bitmap = Bitmap.createScaledBitmap(
                    bitmap, width, height, false);
            URIphoto =  Utils.getImageUri(this, bitmap);
            photo.setImageBitmap(bitmap);
        }
    }
    public static void success() {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.setResult(RESULT_OK, intent);
        activity.finish();
    }

    private void setPhoto(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST);
    }

    private void registrate(){
        final String email, pass, name;
        email = this.email.getText().toString();
        pass = this.pass.getText().toString();
        name = this.name.getText().toString();

        Firebasse.registrate(email, name, pass);
    }

   static   void setFields(String email, String name, String pass){
        Intent intent  = new Intent(activity, Login1.class);
        intent.putExtra("email", email);
        intent.putExtra("name", name);
        intent.putExtra("pass", pass);
        if (URIphoto != null){
            intent.putExtra("photo", URIphoto.toString());
        } else {
            intent.putExtra("photo", "empty");
        }
        activity.setResult(RESULT_OK, intent);
        activity.finish();
    }

    private  boolean checkFields(){
        return !email.getText().toString().equals("")
                && !name.getText().toString().equals("")
                && !pass.getText().toString().equals("")
                && URIphoto != null;
    }
}
