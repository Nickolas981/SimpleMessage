package com.example.nickolas.simplemessage;

import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Nickolas on 20.06.2017.
 */

public class DialogModel {

    ArrayList<MessageModel> messages;
    String me, you;
    String id;
    DialogModelListner listner;

    public DialogModel(String youID, Dialog context) {
        me = Firebasse.getuId();
        this.you = youID;
        id = Utils.generateDialogID(me, you);
        listner = (DialogModelListner) context;
        messages = new ArrayList<>();
        DatabaseReference ref = Firebasse.getmDatebase().getReference();
        ref.child("dialogs").child("dialogs").child(id).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (!dataSnapshot.getKey().equals(me) && !dataSnapshot.getKey().equals(you)) {
                    messages.add(dataSnapshot.getValue(MessageModel.class));
                    listner.add();
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                MessageModel model = dataSnapshot.getValue(MessageModel.class);
                for (int i = 0; i < messages.size(); i++) {
                    if (messages.get(i).equals(model)) {
                        messages.remove(i);
                        listner.remove(i);
                    }
                }

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference r = Firebasse.getmDatebase().getReference();
        Query asd = r.child("dialogs").orderByChild("gaJYLzfscxPAUXmn1rm6pSCUDHY2").equalTo(1);

        asd.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null)
                    Toast.makeText(MainActivity.activity, "asdasdasd", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public int getSize() {
        return messages.size();
    }

    public MessageModel get(int i) {
        return messages.get(i);
    }


    public interface DialogModelListner {
        public void add();

        public void remove(int i);
    }
}
