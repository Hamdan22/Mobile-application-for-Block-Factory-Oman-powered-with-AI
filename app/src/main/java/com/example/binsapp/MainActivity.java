package com.example.binsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;

import com.example.binsapp.databinding.ActivityMainBinding;
import com.google.firebase.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Firebase database;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            int idd = item.getItemId();
            if (idd == R.id.home)
            {
                rplaceFreg(new home());

            }
            else if (idd== R.id.cart)
            {
                rplaceFreg(new cart());
            }
            else if (idd== R.id.account)
                rplaceFreg(new myaccount());
            else if (idd == R.id.searchNav) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Search Feature");
                builder.setMessage("This Feature is coming soon!");
                builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            } else
                rplaceFreg(new home());
            return true;
        });

    }
    private void rplaceFreg(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layouut,fragment);
        fragmentTransaction.commit();
    }
}