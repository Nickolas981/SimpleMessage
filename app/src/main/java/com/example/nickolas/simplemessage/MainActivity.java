package com.example.nickolas.simplemessage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListner {

    public static Activity activity;
    LoginFragment loginFragment;
    private FragmentTransaction fragT;
    static Menu menu;
    Fragment selectedFrag;
    private final String LOGIN_TAG = "LOGIN",
            REG_TAG = "REG",
            MESS_TAG = "MESS",
            USER_LIST_TAG = "USER_LIST",
            DIALOG_LIST_TAG = "DIALOG_LIST_TAG";


    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        Firebasse.setmAuth();

        Firebasse.setmAuthListner();
        Firebasse.setDB();

        if (Firebasse.getCurrentUser() != null && Firebasse.getUser() == null) {
            Firebasse.setUser();
        }
        if (Firebasse.getCurrentUser() != null && !Utils.isMyServiceRunning(MessageService.class)){
            startService(new Intent(this, MessageService.class));
        }
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
        if (Firebasse.getCurrentUser() == null) {
            menu.findItem(R.id.action_exit).setVisible(false);
            menu.findItem(R.id.action_users).setVisible(false);
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Firebasse.getCurrentUser() == null) {
            replaceFragmentWithAnimation(new LoginFragment(), LOGIN_TAG);
        } else if (Firebasse.getUser() == null) {
            Firebasse.setuId();
            Firebasse.setUser();
            replaceFragmentWithAnimation(new DialogList(), DIALOG_LIST_TAG);
        } else {
            replaceFragmentWithAnimation(new DialogList(), DIALOG_LIST_TAG);
        }
    }

    public void replaceFragmentWithAnimation(Fragment fragment, String tag) {
        selectedFrag = getSupportFragmentManager().findFragmentByTag(tag);
        if (selectedFrag == null || !selectedFrag.isVisible()) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
            transaction.replace(R.id.frame_view, fragment, tag);
            transaction.commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_exit) {
            Firebasse.signOut();
            if (Utils.isMyServiceRunning(MessageService.class)){
                stopService(new Intent(this, MessageService.class));
            }
            replaceFragmentWithAnimation(new LoginFragment(), LOGIN_TAG);
        } else if (item.getItemId() == R.id.action_users) {
            replaceFragmentWithAnimation(new UserList(), USER_LIST_TAG);
        }
        return true;
    }

    @Override
    public void onStop() {
        super.onStop();
        Firebasse.stop();
    }

    @Override
    public void registrate() {

    }

    @Override
    public void success() {
        menu.findItem(R.id.action_exit).setVisible(true);
        replaceFragmentWithAnimation(new DialogList(), DIALOG_LIST_TAG);
    }

}
