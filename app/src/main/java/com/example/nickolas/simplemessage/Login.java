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
    private StorageReference mStorageRef;
    static Activity activity;
//    public static User user;


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

    void setUser(User user) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("main");
        myRef.child(Firebasse.getuId()).setValue(user);
        Intent intent = new Intent(Login.this, MainActivity.class);
        setResult(RESULT_OK, intent);
        finish();
    }

//    public static void getToken() {
//        FirebaseUser mUser = MainActivity.mAuth.getCurrentUser();
//        MainActivity.idToken = mUser.getUid();
//    }
//

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


    public static void sucses() {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.setResult(RESULT_OK, intent);
        activity.finish();
    }

    public void uploadFile(String photo) {
        Uri uri = Uri.parse(photo);

        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://simplemessage-abdee.appspot.com").child("avatars");

        StorageReference imageRef = mStorageRef.child(Firebasse.getuId() + ".jpg");

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

//    public void signIn(final String email, String pass) {
//        MainActivity.mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    Firebasse.setuId();
//                    Firebasse.setUser();
////                    getToken();
////                    setUser();
//                    Intent intent = new Intent(Login.this, MainActivity.class);
//                    setResult(RESULT_OK, intent);
//                    finish();
//                } else {
//                    Toast.makeText(Login.this, "Error" + task.getException(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }

//    public static void setUser() {
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("main").child(MainActivity.idToken);
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                user = new User("1", "1");
//                user = dataSnapshot.getValue(User.class);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }

//    public void signIn(final String email, String pass, final String name, final String photo) {
//        MainActivity.mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
////                     getToken();
//                    Firebasse.setuId();
//                    if (!photo.equals("empty")) {
//                        uploadFile(photo);
//                    }
//                    Firebasse.setUser(new User(name, email));
//                } else {
//                    Toast.makeText(Login.this, "Error", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
}
