package com.example.nickolas.simplemessage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText ETemail, ETpass;
    Button logIn, registr;
    static Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_login);
        Firebasse.setContext(Login.this);
        ETemail = (EditText) findViewById(R.id.email);
        ETpass = (EditText) findViewById(R.id.pass);
        logIn = (Button) findViewById(R.id.log_in);
        registr = (Button) findViewById(R.id.reg);

        logIn.setOnClickListener(this);
        registr.setOnClickListener(this);

    }

    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.reg:
                registrate(ETemail.getText().toString(), ETpass.getText().toString());
                break;
            case R.id.log_in:
                Firebasse.login(ETemail.getText().toString(), ETpass.getText().toString());
                break;
        }
    }

    public void registrate(final String email, final String pass) {
        Intent intent = new Intent(this, Registration.class);
        intent.putExtra("email", email);
        intent.putExtra("pass", pass);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            String name, pass, email, photo;
            name = data.getStringExtra("name");
            pass = data.getStringExtra("pass");
            email = data.getStringExtra("email");
            photo = data.getStringExtra("photo");
            Firebasse.login(email, pass, name, photo);
        }
    }

    public static void success() {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.setResult(RESULT_OK, intent);
        activity.finish();
    }
}
