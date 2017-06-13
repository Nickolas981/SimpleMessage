package com.example.nickolas.simplemessage;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText ETemail, ETpass;
    Button logIn, registr;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ETemail = (EditText) findViewById(R.id.email);
        ETpass = (EditText) findViewById(R.id.pass);
        logIn = (Button) findViewById(R.id.log_in);
        registr = (Button) findViewById(R.id.reg);

        logIn.setOnClickListener(this);
        registr.setOnClickListener(this);

    }

    void setUser(User user) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("main");
        myRef.child(MainActivity.idToken).setValue(user);
        Intent intent = new Intent(Login.this, MainActivity.class);
        setResult(RESULT_OK, intent);
        finish();
    }

    public static void getToken() {
        FirebaseUser mUser = MainActivity.mAuth.getCurrentUser();
        MainActivity.idToken = mUser.getUid();
    }


    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.reg:
                registrate(ETemail.getText().toString(), ETpass.getText().toString());
                break;
            case R.id.log_in:
                signIn(ETemail.getText().toString(), ETpass.getText().toString());
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
            signIn(email, pass, name, photo);
        }
    }

    public void uploadFile(String photo) {
        Uri uri = Uri.parse(photo);

        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://simplemessage-abdee.appspot.com").child("avatars");

        StorageReference imageRef = mStorageRef.child(MainActivity.idToken).child("avatar.jpg");

        imageRef.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
//                    Toast.makeText(Login.this, "ok", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Login.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                    ETemail.setText(task.getException().toString());
                    Log.d("Error", task.getException().toString());
                }
            }
        });

    }

    public void signIn(final String email, String pass) {
        MainActivity.mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    getToken();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    setResult(RESULT_OK, intent);
                    getToken();
                } else {
                    Toast.makeText(Login.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void signIn(final String email, String pass, final String name, final String photo) {
        MainActivity.mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    getToken();
                    getToken();
                    if (!photo.equals("empty")) {
                        uploadFile(photo);
                    }
                    setUser(new User(name, email));
                } else {
                    Toast.makeText(Login.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
