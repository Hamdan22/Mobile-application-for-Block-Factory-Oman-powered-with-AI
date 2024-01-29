package com.example.binsapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class home extends Fragment {

     Button chkbtn, cart, account, contact, exist, loginbttn;
     ImageView logoutPic;
    private int[] imageResources = {
            R.drawable.thefound,
            R.drawable.thefound,
            R.drawable.thefound,
            // Add more image resources as needed
    };


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        chkbtn = view.findViewById(R.id.bolcksbtn);
        cart = view.findViewById(R.id.cartbtn);
        account = view.findViewById(R.id.accntbtn);
        contact = view.findViewById(R.id.contactus);
        exist =  view.findViewById(R.id.exitbtn);
        loginbttn = view.findViewById(R.id.loginbtnHome);
        logoutPic = view.findViewById(R.id.loginpageLOGOUTPIC);

        LinearLayout imageContainer = view.findViewById(R.id.imageContainer);

        for (int imageResource : imageResources) {
            ImageView imageView = new ImageView(this.getActivity());
            imageView.setImageResource(imageResource);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(
                    getResources().getDimensionPixelSize(R.dimen.image_width),
                    getResources().getDimensionPixelSize(R.dimen.image_height)));
            imageContainer.addView(imageView);
        }

        exist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.exit(0);
            }
        });

        loginbttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new login();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layouut, fragment).commit();


            }
        });
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new contactUs();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layouut, fragment).commit();
            }
        });
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new myaccount();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layouut, fragment).commit();
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new cart();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layouut, fragment).commit();
            }
        });



        chkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new thefeature();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layouut, fragment).commit();
            }
        });




        if (user != null) {
            loginbttn.setVisibility(View.INVISIBLE);
            logoutPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logoutPic.setVisibility(View.VISIBLE);
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(getActivity(), "User signed out", Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layouut, new home()).commit();
                }
            });




        }
        else
        {
            loginbttn.setVisibility(View.VISIBLE);
            logoutPic.setVisibility(View.INVISIBLE);
        }

        return view;

    }


}