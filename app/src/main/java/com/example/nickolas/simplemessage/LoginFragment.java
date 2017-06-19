package com.example.nickolas.simplemessage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;


public class LoginFragment extends Fragment implements View.OnClickListener{


    View view;
    EditText ETemail, ETpass;
    Button logIn;
    LoginListner loginListner;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);

        ETemail = (EditText) view.findViewById(R.id.email);
        ETpass = (EditText) view.findViewById(R.id.pass);
        logIn = (Button) view.findViewById(R.id.log_in);

        logIn.setOnClickListener(this);

        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginListner = (LoginListner) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.log_in:
                Firebasse.login(ETemail.getText().toString(), ETpass.getText().toString());
                loginListner.success();
                break;
        }
    }

    public interface LoginListner {
        void success();
    }
}
