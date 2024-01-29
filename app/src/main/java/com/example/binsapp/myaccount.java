package com.example.binsapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class myaccount extends Fragment {

   private TextView uname, mobileno, adress, email, password, welometitle;
   Button signoutbtn, changepassBTN, editadressBTN;
   // private  String usernametxt,passwordtxt,emailtxt,locationtxt,mobiletxt;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_myaccount, container, false);

        uname = view.findViewById(R.id.accUSERNAME);
        password = view.findViewById(R.id.accPASSWORD);
        email = view.findViewById(R.id.accEMAIL);
        adress = view.findViewById(R.id.accADRESS);
        mobileno = view.findViewById(R.id.accMOIBLE);
        welometitle = view.findViewById(R.id.prodileupperNAME);
        signoutbtn = view.findViewById(R.id.signoutbtn);
        changepassBTN = view.findViewById(R.id.changePassBtn);
        editadressBTN = view.findViewById(R.id.editAdressBTN);




        if (user != null) {
            String uid = user.getUid();
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        String usernametxt = dataSnapshot.child(uid).child("username").getValue(String.class);
                        String emailtxt = dataSnapshot.child(uid).child("email").getValue(String.class);
                        String mobiletxt = dataSnapshot.child(uid).child("phoneNo").getValue(String.class);
                        String adresstxt = dataSnapshot.child(uid).child("location").getValue(String.class);
                        String passwordtxt = dataSnapshot.child(uid).child("password").getValue(String.class);

                        welometitle.setText("Welocme back "+usernametxt );
                        uname.setText(usernametxt);
                        email.setText(emailtxt);
                        mobileno.setText(mobiletxt);
                        adress.setText(adresstxt);
                        password.setText(passwordtxt);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getActivity()," no user found",Toast.LENGTH_LONG);
                }
            });
            signoutbtn.setVisibility(View.VISIBLE);
            changepassBTN.setVisibility(View.VISIBLE);
            editadressBTN.setVisibility(View.VISIBLE);

            editadressBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showEditAddressDialog();
                }
            });

            changepassBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showEditpasswordDialog();
                }
            });


        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Not Signed in");
            builder.setMessage("Sign in or register to view this page!");
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            signoutbtn.setVisibility(View.INVISIBLE);
            changepassBTN.setVisibility(View.INVISIBLE);
            editadressBTN.setVisibility(View.INVISIBLE);
        }
        signoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getActivity(), "User signed out", Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layouut, new home()).commit();
            }
        });

        return view;
    }

    private void showEditAddressDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Edit Address");
        final EditText input = new EditText(requireContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newAddress = input.getText().toString().trim();
                adress.setText(newAddress);
                updateAddressInDatabase(newAddress);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void updateAddressInDatabase(String newAddress) {
        if (user != null) {
            Map<String, Object> updates = new HashMap<>();
            updates.put("location", newAddress);

            databaseReference.child(user.getUid()).updateChildren(updates)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(requireContext(), "Address updated successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireContext(), "Failed to update address", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void showEditpasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Edit password");
        final EditText input = new EditText(requireContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newpassword = input.getText().toString().trim();
                adress.setText(newpassword);
                updatePasswordInDatabase(newpassword);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
    private void updatePasswordInDatabase(String newPassword) {
        if (user != null) {
            Map<String, Object> updates = new HashMap<>();
            updates.put("password", newPassword);

            databaseReference.child(user.getUid()).updateChildren(updates)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(requireContext(), "password updated successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireContext(), "Failed to update password", Toast.LENGTH_SHORT).show();
                        }
                    });
            user.updatePassword(newPassword)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {

                            Toast.makeText(requireContext(), "Password changed successfully", Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(requireContext(), "Failed to change password", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


}

