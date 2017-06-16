package com.example.nickolas.simplemessage;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * Created by Nickolas on 16.06.2017.
 */

public class Firebasse {

    private static User user;
    private static FirebaseAuth mAuth;
    private static FirebaseDatabase mDatebase;
    private static String uId = "";
    private static FirebaseAuth.AuthStateListener mAuthListener;
    private static Context context;

    public static void setContext(Context context) {
        Firebasse.context = context;
    }

    public Firebasse() {
        user = new User();
        mAuth = FirebaseAuth.getInstance();
        mDatebase = FirebaseDatabase.getInstance();
        uId = "";
    }

    public static void setuId() {
        FirebaseUser mUser = getCurrentUser();
        uId = mUser.getUid();
    }

    public static void setmAuth() {
        mAuth = FirebaseAuth.getInstance();
    }

    public static void setmAuthListner() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
            }
        };
        mAuth.addAuthStateListener(mAuthListener);
    }

    public static void setUser(User userr) {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("main");
//        myRef.child(MainActivity.idToken).setValue(user);
        user = userr;
        DatabaseReference myRef = mDatebase.getReference("main");
        myRef.child(uId).setValue(user);

    }

    public static void setDB() {
        mDatebase = FirebaseDatabase.getInstance();
    }

    public static User getUser() {
        return user;
    }

    public static void signOut() {
        mAuth.signOut();
    }


    public static void stop() {
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public static FirebaseDatabase getmDatebase() {
        return mDatebase;
    }

    public static FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    public static String getuId() {
        return uId;
    }

    public static FirebaseAuth getmAuth() {
        return mAuth;
    }

    public static void setUser() {
        DatabaseReference ref = mDatebase.getReference("main").child(uId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = new User("1", "1");
                user = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void uploadFile(String photo) {
        Uri uri = Uri.parse(photo);

        StorageReference mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://simplemessage-abdee.appspot.com").child("avatars");

        StorageReference imageRef = mStorageRef.child(Firebasse.getuId() + ".jpg");

        imageRef.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
//                    Toast.makeText(Login.this, "ok", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("Error", task.getException().toString());
                }
            }
        });

    }

    public static void login(String mail, String pass) {
        mAuth.signInWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            setuId();
                            Firebasse.setUser();
                            Firebasse.setuId();
                            Login.sucses();
//                    Firebasse.setUser();
////                    getToken();
////                    setUser();
                        } else {

                        }
                    }
                });
    }


    public static void login(final String email, String pass, final String name, final String photo) {
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            setuId();
                            Firebasse.setUser();
                            Firebasse.setuId();
                            if (!photo.equals("empty")){
                                uploadFile(photo);
                            }
                            Firebasse.setUser(new User(name, email));
                        } else {

                        }
                    }
                });
    }
}
