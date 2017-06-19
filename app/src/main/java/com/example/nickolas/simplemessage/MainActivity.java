package com.example.nickolas.simplemessage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, LoginOrRegistrateFragment.LoginOrRegistrateListner, LoginFragment.LoginListner {

    EditText text;
    Button bSend;
    private RecyclerView messageList;
    private CustomMessageAdapter mAdapter;
    public static Activity activity;
    LoginOrRegistrateFragment loginOrRegistrateFragment;
    LoginFragment loginFragment;
    private FragmentTransaction fragT;
    private boolean selected;
    static Menu menu;


    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        loginOrRegistrateFragment = new LoginOrRegistrateFragment();
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        bSend = (Button) findViewById(R.id.send);
        bSend.setOnClickListener(this);

        messageList = (RecyclerView) findViewById(R.id.message_list);
        messageList.setLayoutManager(new mManager(this));
        Firebasse.setmAuth();

        Firebasse.setmAuthListner();
        Firebasse.setDB();

        text = (EditText) findViewById(R.id.text);

        showMessages();

    }

    void out() {
        fragT = getSupportFragmentManager().beginTransaction();
        fragT.replace(R.id.frame_view, loginOrRegistrateFragment);
        fragT.commit();
    }

    void test() {
        DatabaseReference mRef = Firebasse.getmDatebase().getReference().child("main");
        mRef.orderByChild("email").equalTo("kolin98111@gmail.com").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    User u = d.getValue(User.class);
                    getSupportActionBar().setTitle(u.name);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        MainActivity.menu = menu;
        if (Firebasse.getCurrentUser() == null){
            menu.findItem(R.id.action_exit).setVisible(false);
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Firebasse.getCurrentUser() == null) {
            Toast.makeText(this, "out", Toast.LENGTH_SHORT).show();
//            logIn();
            out();
        } else if (Firebasse.getUser() == null) {
            Firebasse.setuId();
            Firebasse.setUser();
        } else{
            fragT = getSupportFragmentManager().beginTransaction();
            fragT.replace(R.id.frame_view, new MessageFragment());
            fragT.commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Firebasse.setuId();
                Firebasse.setUser();
                Firebasse.setDB();
                mAdapter = null;
                showMessages();
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_exit) {
            Firebasse.signOut();
//            logIn();
            out();
            mAdapter = null;
        }
        return true;
    }

    void logIn() {
        Intent login = new Intent(MainActivity.this, Login1.class);
        startActivityForResult(login, 1);
    }


    @Override
    public void onStop() {
        super.onStop();
        Firebasse.stop();
    }


    void sendMessage() {
        MessageModel messageModel = new MessageModel(Firebasse.getCurrentUser().getEmail(), text.getText().toString());
        DatabaseReference myRef = Firebasse.getmDatebase().getReference("messages");
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
        DatabaseReference ref = Firebasse.getmDatebase().getReference("messages");

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (mAdapter == null) {
                    mAdapter = new CustomMessageAdapter(new ArrayList<MessageModel>(), MainActivity.this);
                    messageList.setAdapter(mAdapter);
                }
                mAdapter.addItem(dataSnapshot.getValue(MessageModel.class));
                messageList.scrollToPosition(mAdapter.getItemCount() - 1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                mAdapter.removeItem(dataSnapshot.getValue(MessageModel.class));
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void login() {
        loginFragment = new LoginFragment();
        fragT = getSupportFragmentManager().beginTransaction();
        fragT.replace(R.id.frame_view, loginFragment);
        fragT.commit();
    }

    @Override
    public void registrate() {

    }

    @Override
    public void success() {
        fragT = getSupportFragmentManager().beginTransaction();
        fragT.replace(R.id.frame_view, new MessageFragment());
        fragT.commit();
    }
}
