package com.example.binsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class login extends Fragment {

    EditText loginUsernameEMAIL, loginpassword;
    Button loginBtn, HomeLoginbtn;
    TextView notsigned;

    FirebaseAuth authprofile;



    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);


        loginUsernameEMAIL = view.findViewById(R.id.usernameLogin);
        loginpassword = view.findViewById(R.id.passwordLogin);
        notsigned = view.findViewById(R.id.notsignupText);
        loginBtn = view.findViewById(R.id.loginButton);
        authprofile = FirebaseAuth.getInstance();


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userUsername = loginUsernameEMAIL.getText().toString();
                String userPassword = loginpassword.getText().toString();

                if (!validateUSER() && !!validatePassword())
                {

                }
                else
                {

                    loginUser(userUsername, userPassword);

                }


            }
        });


        notsigned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser firebaseUser= authprofile.getCurrentUser();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layouut, new signup()).commit();
            }
        });


        return view;
    }

    private void loginUser(String userUsername, String userPassword) {

        authprofile.signInWithEmailAndPassword(userUsername,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {

                            Fragment fragment = new myaccount();
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frame_layouut, fragment).commit();

                            Toast.makeText(getActivity(), "welcome back!" , Toast.LENGTH_LONG).show();
                        }


                else
                {
                    Toast.makeText(getActivity(), "something is wrong", Toast.LENGTH_LONG).show();

                }
            }
        });
    }


    public Boolean validatePassword() {
        String passVal = loginpassword.getText().toString();

        if (passVal.isEmpty()) {
            loginpassword.setError(" enter password");
            return false;
        } else {
            loginpassword.setError(null);
            return true;
        }
    }
    public Boolean validateUSER() {
        String userval = loginUsernameEMAIL.getText().toString();

        if (userval.isEmpty()) {
            loginUsernameEMAIL.setError(" enter your username");
            return false;
        } else {
            loginUsernameEMAIL.setError(null);
            return true;
        }

    }


}