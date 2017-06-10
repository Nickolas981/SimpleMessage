package com.example.nickolas.simplemessage;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    static String idToken;
    static FirebaseAuth mAuth;
    static FirebaseAuth.AuthStateListener mAuthListener;
    EditText text;
    Button bSend;
    public TextView result;


    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        bSend = (Button) findViewById(R.id.send);
        bSend.setOnClickListener(this);
        result = (TextView) findViewById(R.id.result);


        MainActivity.mAuth = FirebaseAuth.getInstance();
        MainActivity.mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(MainActivity.this, "signed_in:", Toast.LENGTH_SHORT).show();
                    Login.getToken();
                } else {
                    Toast.makeText(MainActivity.this, "signed_out", Toast.LENGTH_SHORT).show();
                    logIn();
                }
            }
        };
        MainActivity.mAuth.addAuthStateListener(MainActivity.mAuthListener);

        text = (EditText) findViewById(R.id.text);
        showMessages();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_exit)
        {
            mAuth.signOut();
            logIn();
        }
        return true;
    }

    void logIn(){
        Intent login = new Intent(MainActivity.this, Login.class);
        startActivityForResult(login, 1);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void writeNameToDB(String name){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("main");

        myRef.child(idToken).child("last_name").setValue(name).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void sendMessage(String mess){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("messages");
        text.setText("");
        myRef =  myRef.push();

        myRef.child("message").setValue(mess);
        myRef.child("message").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getSupportActionBar().setTitle(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        myRef.child("email").setValue(mAuth.getCurrentUser().getEmail());
    }

    @Override
    public void onClick(View v) {
        int  id = v.getId();
        switch (id){
            case R.id.send:
                sendMessage(text.getText().toString());
        }
    }

    public void showMessages(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("messages");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                result.setText("");
                for (DataSnapshot message: dataSnapshot.getChildren()){
                    String obj = message.child("message").getValue().toString();
                    result.append(obj + "\n");
//                    result.append(message.child("message").getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
