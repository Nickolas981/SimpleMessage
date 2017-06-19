package com.example.nickolas.simplemessage;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class LoginOrRegistrateFragment extends Fragment implements View.OnClickListener {


    View view;
    Button login, registrate;
    LoginOrRegistrateListner listner;

    public static LoginOrRegistrateFragment newInstance(String param1, String param2) {
        LoginOrRegistrateFragment fragment = new LoginOrRegistrateFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_login_or_registrate, container, false);
        login = (Button) view.findViewById(R.id.log_in);
        registrate = (Button) view.findViewById(R.id.registration);

        login.setOnClickListener(this);
        registrate.setOnClickListener(this);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listner = (LoginOrRegistrateListner) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.registration:
                Toast.makeText(MainActivity.activity, "REG", Toast.LENGTH_SHORT).show();
                break;
            case R.id.log_in:
                Toast.makeText(MainActivity.activity, "LOG", Toast.LENGTH_SHORT).show();
                listner.login();
                break;
        }
    }

    public interface LoginOrRegistrateListner {
        void login();
        void registrate();
    }
}
