package com.example.nickolas.simplemessage;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;



public class DialogListModel {

    private ArrayList<Dialog> dialogs;
    private DialogListListner listner;
    private boolean suc;

    public DialogListModel(DialogList context) {
        dialogs = new ArrayList<>();

        listner = (DialogListListner) context;
        Firebasse.setUser(this);
    }

    public void setDialogs(){
        DatabaseReference ref = Firebasse.getmDatebase().getReference();
        final Dialog[] d = new Dialog[1];
        Query q = ref.child("dialogs").child("users").orderByChild(Firebasse.getuId()).equalTo(Firebasse.getUser().getName());

        q.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                d[0] = new Dialog();
                d[0].key = dataSnapshot.getKey();
                d[0].last = dataSnapshot.child("lastMessage").getValue(MessageModel.class);
                d[0].photo = d[0].key.replace(Firebasse.getuId(), "");
                d[0].name =  dataSnapshot.child(d[0].photo).getValue(String.class);
//                d[0].name = nameEmail[1];
//                d[0].email = nameEmail[0];
                if (!suc){
                    suc = true;
                    listner.success();
                }
                dialogs.add(d[0]);
                listner.add();
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

    public Dialog get(int i){
        return dialogs.get(i);
    }

    public int getSize(){
        return dialogs.size();
    }

    class Dialog {
        String key;
        MessageModel last;
        String name;
//        String email;
        String photo;
    }

    interface DialogListListner {
        void add();
        void success();
    }


}
