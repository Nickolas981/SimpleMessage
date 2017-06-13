package com.example.nickolas.simplemessage;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity implements View.OnClickListener{

    ImageView photo;
    EditText name,email, pass;
    Button submit, cancel;
    private final int GALLERY_REQUEST = 1;
    Uri URIphoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            this.photo.setImageURI(URIphoto);
        }
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

        MainActivity.mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    setFields(email, name, pass);
                } else {
                    Toast.makeText(Registration.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void setFields(String email, String name, String pass){
        Intent intent  = new Intent(this, Login.class);
        intent.putExtra("email", email);
        intent.putExtra("name", name);
        intent.putExtra("pass", pass);
        if (URIphoto != null){
            intent.putExtra("photo", URIphoto.toString());
        } else {
            intent.putExtra("photo", "empty");
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    private  boolean checkFields(){
        return !email.getText().toString().equals("") && !name.getText().toString().equals("") && !pass.getText().toString().equals("");
    }
}
