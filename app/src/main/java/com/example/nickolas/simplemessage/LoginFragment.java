package com.example.nickolas.simplemessage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

import static android.app.Activity.RESULT_OK;


public class LoginFragment extends Fragment implements View.OnClickListener {


    View view;
    EditText ETemail, ETpass;
    Button logIn, registrate;
    public static LoginListner loginListner;

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
        if (MainActivity.menu != null)
            MainActivity.menu.findItem(R.id.action_exit).setVisible(false);
        ETemail = (EditText) view.findViewById(R.id.email);
        ETpass = (EditText) view.findViewById(R.id.pass);
        logIn = (Button) view.findViewById(R.id.log_in);
        registrate = (Button) view.findViewById(R.id.registration);

        logIn.setOnClickListener(this);
        registrate.setOnClickListener(this);

        return view;

    }

    void deactivate() {
        ETemail.setEnabled(false);
        ETpass.setEnabled(false);
        logIn.setVisibility(View.GONE);
        registrate.setEnabled(false);
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
                if (checkFields()) {
                    Firebasse.login(ETemail.getText().toString(), ETpass.getText().toString());
                    deactivate();
                } else {
                    Toast.makeText(MainActivity.activity, "Some fields are incorrect", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.registration:
                Intent intent = new Intent(MainActivity.activity, Registration.class);
                intent.putExtra("pass", ETpass.getText().toString());
                intent.putExtra("email", ETemail.getText().toString());
                startActivityForResult(intent, 1);
                break;
        }
    }

    private boolean checkFields() {
        return !Objects.equals(ETpass.getText().toString(), "") && !Objects.equals(ETemail.getText().toString(), "");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String name, pass, email, photo;
            name = data.getStringExtra("name");
            pass = data.getStringExtra("pass");
            email = data.getStringExtra("email");
            photo = data.getStringExtra("photo");
            ETemail.setText(email);
            ETpass.setText(pass);
            deactivate();
            Firebasse.login(email, pass, name, photo);
        }
    }

    public interface LoginListner {
        void success();

        void registrate();
    }
}
