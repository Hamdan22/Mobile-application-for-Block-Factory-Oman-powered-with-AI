package com.example.binsapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class contactUs extends Fragment {
    private EditText fullnameTXT, phonenoTXT, messageTXT;
    private Button submitbtn;
    private DatabaseReference databaseReference;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_contact_us, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("contactUs_messages");
        fullnameTXT = view.findViewById(R.id.fullnameCONTACT);
        phonenoTXT = view.findViewById(R.id.phoneCONTACT);
        messageTXT = view.findViewById(R.id.messageCONTACT);
        submitbtn = view.findViewById(R.id.submitCONTACT);

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitMessage();
            }
        });

        return view;
    }

    private void submitMessage() {
        String fullName = fullnameTXT.getText().toString().trim();
        String phoneNumber = phonenoTXT.getText().toString().trim();
        String message = messageTXT.getText().toString().trim();

        if (!fullName.isEmpty() && !phoneNumber.isEmpty() && !message.isEmpty()) {

            String submissionId = databaseReference.push().getKey();


            contactHelper contactUsMessage = new contactHelper(fullName, phoneNumber, message);


            databaseReference.child(submissionId).setValue(contactUsMessage);


            Toast.makeText(getActivity(), "Message submitted successfully!", Toast.LENGTH_SHORT).show();


            fullnameTXT.getText().clear();
            phonenoTXT.getText().clear();
            messageTXT.getText().clear();
        } else {
            Toast.makeText(getActivity(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
        }
    }

}