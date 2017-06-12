package com.example.nickolas.simplemessage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText ETemail, ETpass;
    Button logIn, registr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ETemail = (EditText) findViewById(R.id.email);
        ETpass = (EditText) findViewById(R.id.pass);
        logIn = (Button) findViewById(R.id.log_in);
        registr = (Button) findViewById(R.id.reg);

        logIn.setOnClickListener(this);
        registr.setOnClickListener(this);

    }

    void setUser(User user) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("main");
        myRef.child(MainActivity.idToken).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(Login.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void getToken() {
        FirebaseUser mUser = MainActivity.mAuth.getCurrentUser();
        MainActivity.idToken = mUser.getUid();
    }


    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.reg:
                registrate(ETemail.getText().toString(), ETpass.getText().toString());
                break;
            case R.id.log_in:
                signIn(ETemail.getText().toString(), ETpass.getText().toString());
                break;
        }
    }


    public void registrate(final String email, final String pass) {
        Intent intent = new Intent(this, Registration.class);
        intent.putExtra("email", email);
        intent.putExtra("pass", pass);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2 && resultCode ==RESULT_OK && data != null){
            String name, pass, email;
            name = data.getStringExtra("name");
            pass = data.getStringExtra("pass");
            email = data.getStringExtra("email");
            signIn(email, pass);
            setUser(new User(name, email));
        }
    }

    public void signIn(final String email, String pass) {
        MainActivity.mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    getToken();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(Login.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
