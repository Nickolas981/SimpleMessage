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
import android.widget.ListView;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static com.example.nickolas.simplemessage.Reversed.reversed;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    static String idToken;
    static FirebaseAuth mAuth;
    static FirebaseAuth.AuthStateListener mAuthListener;
    EditText text;
    Button bSend;
    public TextView result;
    public FirebaseDatabase mDatebase;


    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
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
                    setDB();
                    showMessages();
                } else {
                    Toast.makeText(MainActivity.this, "signed_out", Toast.LENGTH_SHORT).show();
                    logIn();
                }
            }
        };
        MainActivity.mAuth.addAuthStateListener(MainActivity.mAuthListener);

        text = (EditText) findViewById(R.id.text);
        if (mDatebase == null)
            setDB();
        showMessages();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                Login.getToken();
                setDB();
                showMessages();
            }
        }
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

    void setDB(){
        mDatebase = FirebaseDatabase.getInstance();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void writeNameToDB(String name){
        DatabaseReference myRef = mDatebase.getReference("main");
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
        Message message = new Message(mAuth.getCurrentUser().getEmail(), mess);
        DatabaseReference myRef = mDatebase.getReference("messages");
        text.setText("");
        myRef =  myRef.push();
        myRef.setValue(message);
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
        final int size = 5;

        DatabaseReference ref = mDatebase.getReference("messages");
        Query query = ref.limitToLast(size);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                result.setText("");
                final ArrayList<Message> ALmessage = new ArrayList<>();
                for (DataSnapshot message: dataSnapshot.getChildren()){
                    ALmessage.add(message.getValue(Message.class));
                }
                for (Message message:reversed(ALmessage)){
                    result.append(message + "    ");
                    result.append(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", message.getTimeMessage()));
                    result.append("\n\n\n");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
