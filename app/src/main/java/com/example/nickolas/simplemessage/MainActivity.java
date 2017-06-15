package com.example.nickolas.simplemessage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    static String idToken;
    static FirebaseAuth mAuth;
    static FirebaseAuth.AuthStateListener mAuthListener;
    EditText text;
    Button bSend;
    private RecyclerView messageList;
    public FirebaseDatabase mDatebase;
    private CustomMessageAdapter mAdapter;


    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        bSend = (Button) findViewById(R.id.send);
        bSend.setOnClickListener(this);

        messageList = (RecyclerView) findViewById(R.id.message_list);
        messageList.setLayoutManager(new LinearLayoutManager(this));
        mAuth = FirebaseAuth.getInstance();
        setListner();
        MainActivity.mAuth.addAuthStateListener(MainActivity.mAuthListener);

        text = (EditText) findViewById(R.id.text);


        if (mDatebase == null)
            setDB();
        showMessages();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAuth.getCurrentUser() == null) {
            Toast.makeText(this, "out", Toast.LENGTH_SHORT).show();
            logIn();
        } else if (Login.user == null) {
            Login.getToken();
            Login.setUser();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Login.getToken();
                Login.setUser();
                mAdapter = null;
                showMessages();
                setDB();
            }
        }
    }

    private void setListner() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(MainActivity.this, "signed_in:", Toast.LENGTH_SHORT).show();
//                    Login.getToken();
//                    setDB();
//                    showMessages();
//                    Login.setUser();
                } else {
//                    Toast.makeText(MainActivity.this, "signed_out", Toast.LENGTH_SHORT).show();
//                    logIn();
                }
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_exit) {
            mAuth.signOut();
            logIn();
            mAdapter = null;
        }
        return true;
    }

    void logIn() {
        Intent login = new Intent(MainActivity.this, Login.class);
        startActivityForResult(login, 1);
    }

    void setDB() {
        mDatebase = FirebaseDatabase.getInstance();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    void sendMessage() {
        MessageModel messageModel = new MessageModel(mAuth.getCurrentUser().getEmail(), text.getText().toString());
        DatabaseReference myRef = mDatebase.getReference("messages");
        text.setText("");
        myRef = myRef.push();
        myRef.setValue(messageModel);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.send:
                sendMessage();
        }
    }


    public void showMessages() {
        DatabaseReference ref = mDatebase.getReference("messages");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<MessageModel> ALmessage = new ArrayList<>();
                for (DataSnapshot message : dataSnapshot.getChildren()) {
                    ALmessage.add(message.getValue(MessageModel.class));
                }
                if (mAdapter == null) {
                    mAdapter = new CustomMessageAdapter(ALmessage, MainActivity.this);
                    messageList.setAdapter(mAdapter);
                } else {
                    mAdapter.addItems(ALmessage);
                }
                messageList.scrollToPosition(mAdapter.getItemCount() - 1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
