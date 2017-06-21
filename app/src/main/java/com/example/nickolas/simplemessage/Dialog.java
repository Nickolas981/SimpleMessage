package com.example.nickolas.simplemessage;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.r0adkll.slidr.Slidr;

public class Dialog extends AppCompatActivity implements DialogModel.DialogModelListner, View.OnClickListener{

    EditText editText;
    ImageView send;
    RecyclerView messageList;
    CustomDialogAdapter mAdapter;
    DialogModel dialogModel;
    String name, email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        Slidr.attach(this);
        editText = (EditText) findViewById(R.id.et_message);
        send = (ImageView) findViewById(R.id.send_btn);
        messageList = (RecyclerView)  findViewById(R.id.message_list);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
//        email = intent.getStringExtra("email");
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        send.setOnClickListener(this);
        messageList.setLayoutManager(new mManager(MainActivity.activity));
        dialogModel = new DialogModel(id, this);
        mAdapter = new CustomDialogAdapter(dialogModel);
        messageList.setAdapter(mAdapter);
    }

    @Override
    public void add() {
        mAdapter.addItem();
        messageList.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    @Override
    public void remove(int i) {
        mAdapter.removeItem(i);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.send_btn:
                sendMessage();
                break;
        }
    }

    private void sendMessage() {
        if (dialogModel.messages.size() == 0){
            DatabaseReference reference = Firebasse.getmDatebase().getReference();
            reference = reference.child("dialogs").child("users").child(dialogModel.id);
            reference.child(dialogModel.me).setValue(Firebasse.getUser().getName());
            reference.child(dialogModel.you).setValue(name);
//            reference.child(dialogModel.you).setValue(true);

        }
        final MessageModel messageModel = new MessageModel(Firebasse.getCurrentUser().getEmail(), editText.getText().toString());
        DatabaseReference myRef = Firebasse.getmDatebase().getReference();
        editText.setText("");

        myRef = myRef.child("dialogs").child("dialogs").child(dialogModel.id).push();
        myRef.setValue(messageModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()){
                    Toast.makeText(MainActivity.activity, task.getException().toString(), Toast.LENGTH_LONG).show();
                }else {
                    DatabaseReference reference = Firebasse.getmDatebase().getReference();
                    reference = reference.child("dialogs").child("users").child(dialogModel.id);
                    reference.child("lastMessage").setValue(messageModel);
                }
            }
        });
    }
}
