package com.example.nickolas.simplemessage;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListner {

    public static Activity activity;
    LoginFragment loginFragment;
    private FragmentTransaction fragT;
    static Menu menu;


    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        Firebasse.setmAuth();

        Firebasse.setmAuthListner();
        Firebasse.setDB();


    }

    void out() {
        fragT = getSupportFragmentManager().beginTransaction();
        fragT.replace(R.id.frame_view, new LoginFragment());
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
        if (Firebasse.getCurrentUser() == null) {
            menu.findItem(R.id.action_exit).setVisible(false);
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Firebasse.getCurrentUser() == null) {
            out();
        } else if (Firebasse.getUser() == null) {
            Firebasse.setuId();
            Firebasse.setUser();
            fragT = getSupportFragmentManager().beginTransaction();
            fragT.replace(R.id.frame_view, new MessageFragment());
            fragT.commit();
        } else {
            fragT = getSupportFragmentManager().beginTransaction();
            fragT.replace(R.id.frame_view, new MessageFragment());
            fragT.commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_exit) {
            Firebasse.signOut();
            out();
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
        fragT = getSupportFragmentManager().beginTransaction();
        fragT.replace(R.id.frame_view, new MessageFragment());
        fragT.commit();
    }
}
