//package com.example.nickolas.simplemessage;
//
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//
//
//public class LoginFragment extends Fragment implements View.OnClickListener{
//
//
//    View view;
//    EditText ETemail, ETpass;
//    Button logIn, registr;
//
//    public LoginFragment() {
//        // Required empty public constructor
//    }
//
//
//    // TODO: Rename and change types and number of parameters
//    public static LoginFragment newInstance(String param1, String param2) {
//
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.fragment_login, container, false);
//
//        ETemail = (EditText) view.findViewById(R.id.email);
//        ETpass = (EditText) view.findViewById(R.id.pass);
//        logIn = (Button) view.findViewById(R.id.log_in);
//        registr = (Button) view.findViewById(R.id.reg);
//
//        logIn.setOnClickListener(this);
//        registr.setOnClickListener(this);
//
//        return view;
//
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//    }
//
//    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//        switch (id) {
//            case R.id.reg:
//                registrate(ETemail.getText().toString(), ETpass.getText().toString());
//                break;
//            case R.id.log_in:
//                signIn(ETemail.getText().toString(), ETpass.getText().toString());
//                break;
//        }
//    }
//
//    public void signIn(final String email, String pass) {
//        MainActivity.mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    getToken();
//                    setUser();
//                    Intent intent = new Intent(Login.this, MainActivity.class);
//                    setResult(RESULT_OK, intent);
//                    finish();
//                } else {
//                    Toast.makeText(Login.this, "Error" + task.getException(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
//}
