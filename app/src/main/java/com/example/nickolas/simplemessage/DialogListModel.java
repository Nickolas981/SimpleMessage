package com.example.nickolas.simplemessage;

import android.content.Context;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;

/**
 * Created by Nickolas on 20.06.2017.
 */

public class DialogListModel {

    ArrayList<Dialog1> messages;
    DialogModel.DialogModelListner listner;

    public DialogListModel(Context context) {
        DatabaseReference ref = Firebasse.getmDatebase().getReference();
        final Dialog1[] d = new Dialog1[1];
        Query q = ref.child("dialogs").child("users").orderByChild(Firebasse.getuId()).equalTo(Firebasse.getUser().getEmail() + "/" + Firebasse.getUser().getName());

        q.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                d[0] = new Dialog1();
                d[0].key = dataSnapshot.getKey();
                d[0].last = dataSnapshot.child("lastMessage").getValue(MessageModel.class);
                d[0].photo = d[0].key.replace(Firebasse.getuId(), "");
                String[] nameEmail = dataSnapshot.child(d[0].photo).getValue(String.class).split("/");
                d[0].name = nameEmail[1];
                d[0].email = nameEmail[0];
                messages.add(d[0]);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    class Dialog1 {
        String key;
        MessageModel last;
        String name;
        String email;
        String photo;
    }

    interface DialogListListner {
        void add();
    }


}
