package com.example.binsapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class signup extends Fragment {

    EditText urname, psrd, email, location, phoneNo;
    Button signupbtn;
    TextView gotacc;


    private FirebaseAuth mAuth;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getInstance().getReference();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        gotacc = view.findViewById(R.id.alreadygotacc);
        urname = view.findViewById(R.id.usernameSignup);
        psrd = view.findViewById(R.id.passwordSignup);
        email = view.findViewById(R.id.emailSignup);
        location = view.findViewById(R.id.addressSignup);
        phoneNo = view.findViewById(R.id.phoneNoSignup);
        mAuth = FirebaseAuth.getInstance();



        signupbtn = view.findViewById(R.id.SignuoBTN);

        gotacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new login();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layouut, fragment).commit();

            }
        });

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
        return view;
    }

    private void signUp() {
        String username = urname.getText().toString();
        String eemail = email.getText().toString();
        String locationn = location.getText().toString();
        String passwordd = psrd.getText().toString();
        String mobilee = phoneNo.getText().toString();

        mAuth.createUserWithEmailAndPassword(eemail, passwordd)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {

                        FirebaseUser user = mAuth.getCurrentUser();
                            saveExtraValues(user.getUid(),username,eemail,passwordd,locationn,mobilee);
                        Toast.makeText(getActivity(), "regestried sucssfully", Toast.LENGTH_SHORT).show();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layouut, new home()).commit();
                    } else {
                        Toast.makeText(getActivity(), "the regstration is faild", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void saveExtraValues(String userId, String username, String email, String password, String location, String mobile) {
        helperClass user = new helperClass(username,password,email,location,mobile);
        reference.child(userId).setValue(user);
    }

}


