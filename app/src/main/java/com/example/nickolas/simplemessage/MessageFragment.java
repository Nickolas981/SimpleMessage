package com.example.nickolas.simplemessage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;


public class MessageFragment extends Fragment  implements View.OnClickListener{

    private EditText text;
    private Button bSend;
    private RecyclerView messageList;
    private CustomMessageAdapter mAdapter;
    private ProgressBar progressBar;

    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        messageList = (RecyclerView) view.findViewById(R.id.message_list);
        messageList.setLayoutManager(new mManager(MainActivity.activity));
        text = (EditText) view.findViewById(R.id.text);
        bSend = (Button) view.findViewById(R.id.send);
        bSend.setOnClickListener(this);
        showMessages();

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        sendMessage();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    void sendMessage() {
        MessageModel messageModel = new MessageModel(Firebasse.getCurrentUser().getEmail(), text.getText().toString());
        DatabaseReference myRef = Firebasse.getmDatebase().getReference("messages");
        text.setText("");
        myRef = myRef.push();
        myRef.setValue(messageModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()){
                    Toast.makeText(MainActivity.activity, task.getException().toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void showMessages() {

        DatabaseReference ref = Firebasse.getmDatebase().getReference("messages");

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (mAdapter == null) {
                    mAdapter = new CustomMessageAdapter(new ArrayList<MessageModel>(), MainActivity.activity);
                    messageList.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
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
}
