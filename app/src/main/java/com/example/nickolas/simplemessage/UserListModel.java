package com.example.nickolas.simplemessage;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class UserListModel {

    private ArrayList<User> users;
    UserListModelListner userListModelListner;

    public UserListModel(UserList userList) {
        users = new ArrayList<>();
        userListModelListner = (UserListModelListner) userList;
        Firebasse.setUser(this);
    }


    public int getCount() {
        return users.size();
    }

    public User get(int i) {
        return users.get(i);
    }

    public void setUsers() {
        DatabaseReference reference = Firebasse.getmDatebase().getReference().child("main");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (!Firebasse.getUser().email.equals(data.getValue(User.class).email))
                        users.add(data.getValue(User.class));
                }
                userListModelListner.userListSuccess();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public interface UserListModelListner {
        void userListSuccess();
    }
}
