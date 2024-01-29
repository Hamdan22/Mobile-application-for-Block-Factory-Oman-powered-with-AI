package com.example.binsapp;

import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;


public class thefeature extends Fragment {


    EditText hightTXT, widthTXT, costmizedTXT;
    TextView resultAi, resultCos;
    Button btnCheckttAI, btnChecktCostomiz, BtnAddtoCartAI, BtnAddtocartCos;
    double nOofBricks, totalPriceOfCustomize;
    double unitPrice = 0.1;
    private DatabaseReference dbreference;
    Double totalBricksRequired, finalPrice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thefeature, container, false);



        hightTXT = view.findViewById(R.id.txthightforAI);
        widthTXT = view.findViewById(R.id.txtwidthforAI);
        resultAi = view.findViewById(R.id.txtresultFromAI);
        btnChecktCostomiz = view.findViewById(R.id.Checkcustomize);
        resultCos = view.findViewById(R.id.custTxtResult);
        costmizedTXT = view.findViewById(R.id.txtCustomize);
        resultCos = view.findViewById(R.id.custTxtResult);
        BtnAddtoCartAI = view.findViewById(R.id.btnAIvalueToCart);
        BtnAddtocartCos = view.findViewById(R.id.btnCustomizeToCart);

        dbreference = FirebaseDatabase.getInstance().getReference().child("cart");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();




        btnCheckttAI= view.findViewById(R.id.checkbtnAI);

        btnCheckttAI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (user != null) {
                    if (TextUtils.isEmpty(hightTXT.getText()) && TextUtils.isEmpty(widthTXT.getText()) || !TextUtils.isDigitsOnly(hightTXT.getText()) && !TextUtils.isDigitsOnly(widthTXT.getText())) {
                        Toast.makeText(getActivity(), "Enter correct values", Toast.LENGTH_SHORT).show();
                    } else {
                        try {

                            double enteredWidth = Double.parseDouble(widthTXT.getText().toString());
                            double enteredHeight = Double.parseDouble(hightTXT.getText().toString());

                            List<DataPoint> dataset = CsvReader.readCsvDataFromAssets(getActivity(), "small_house_bricks_dataset.csv");

                            DataPoint closestMatch = findClosestMatch(dataset, enteredWidth, enteredHeight);

                            if (closestMatch != null) {
                                totalBricksRequired = closestMatch.getTotalBricksRequired();
                                finalPrice = unitPrice * totalBricksRequired;
                                resultAi.setText("Total Bricks Required: " + totalBricksRequired + " Price:" + finalPrice + " OMR");
                            } else {
                                resultAi.setText("No matching data point found.");
                            }
                        } catch (NumberFormatException e) {
                            resultAi.setText("Invalid input. Please enter valid numeric values for width and height.");
                        }
                        String userid = user.getUid();

                        BtnAddtoCartAI.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String uEmail = user.getEmail();
                                String itemName = "bricks AI";
                                String itemId = dbreference.push().getKey();
                                CartItem cartItem = new CartItem(itemId, itemName, totalBricksRequired, finalPrice, uEmail, userid);
                                dbreference.child(itemId).setValue(cartItem);
                                Toast.makeText(getActivity(), "Item added to cart", Toast.LENGTH_SHORT).show();

                            }
                        });


                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "Please Sign in or register", Toast.LENGTH_SHORT).show();

                }
            }
        });

        btnChecktCostomiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (user!= null) {
                    if (TextUtils.isEmpty(costmizedTXT.getText()) || !TextUtils.isDigitsOnly(costmizedTXT.getText())) {


                        Toast.makeText(getActivity(), "Enter correct values", Toast.LENGTH_SHORT).show();
                    }else {
                        nOofBricks = Double.parseDouble(costmizedTXT.getText().toString());
                        totalPriceOfCustomize = (nOofBricks * unitPrice);
                        resultCos.setText("Total Bricks Required: " + nOofBricks + " Price:" + totalPriceOfCustomize + " OMR");

                        BtnAddtocartCos.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String uEmail = user.getEmail();
                                String itemName = "bricks customized";
                                String itemId = dbreference.push().getKey();
                                CartItem cartItem = new CartItem(itemId, itemName, nOofBricks, totalPriceOfCustomize, uEmail,user.getUid());
                                dbreference.child(itemId).setValue(cartItem);
                                Toast.makeText(getActivity(), "Item added to cart", Toast.LENGTH_SHORT).show();
                            }
                        });}

                }
                else
                {
                    Toast.makeText(getActivity(), "Please Sign in or register", Toast.LENGTH_SHORT).show();

                }


            }
        });


        return view;
    }


    private DataPoint findClosestMatch(List<DataPoint> dataset, double newWidth, double newHeight) {
        DataPoint closestMatch = null;
        double minDistance = Double.MAX_VALUE;

        for (DataPoint dataPoint : dataset) {
            double distance = calculateDistance(dataPoint.getWidth(), dataPoint.getHeight(), newWidth, newHeight);
            if (distance < minDistance) {
                minDistance = distance;
                closestMatch = dataPoint;
            }
        }

        return closestMatch;
    }
    private double calculateDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}