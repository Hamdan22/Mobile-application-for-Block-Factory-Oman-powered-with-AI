package com.example.binsapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class checkout extends Fragment {

    DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference().child("cart");
    DatabaseReference usrRef = FirebaseDatabase.getInstance().getReference().child("users");
    DatabaseReference confirmedOrdersReference = FirebaseDatabase.getInstance().getReference("confirmed_orders");
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    Button confBTN;
    TextView totalBricks, totalPrice, delveryadress;
    private OrderAdapter orderAdapter;
    RadioButton b1Savedadress, b2anotherAdress;
    RadioGroup adressoptions;
    String orderId,  cartItemid,  deliveryAddress,  totalCost, userid,   userEmail,  userName, userMobile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_checkout, container, false);
        EditText enterlocation;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        totalBricks = view.findViewById(R.id.totalProductsNumber);
        totalPrice = view.findViewById(R.id.totalPriceOfProduct);
        b1Savedadress = view.findViewById(R.id.getsavedadress);
        b2anotherAdress = view.findViewById(R.id.newAdressRadioBtn);
        adressoptions = view.findViewById(R.id.adressoptions);
        delveryadress = view.findViewById(R.id.theadresstobedelivered);
        confBTN = view.findViewById(R.id.confBTNN);


        if (user!= null) {
            String uId = user.getUid();

            retrieveCartItemsForUser(uId);
        }
        else
        {
            builder.setTitle("Not signIn");
            builder.setMessage("Please sign in or register to view this page");
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        adressoptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.getsavedadress)
                {
                    getSavedAdressDB();
                }
                else if (checkedId == R.id.newAdressRadioBtn)
                {

                    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                    builder.setTitle("Edit Address");
                    final EditText input = new EditText(requireContext());
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    builder.setView(input);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String newAddress = input.getText().toString().trim();
                            delveryadress.setText(newAddress);


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
            }
        });

        confBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (user!= null)
                {
                    confirmPurchase();
                }
                else
                {
                    builder.setTitle("Not signIn");
                    builder.setMessage("Please sign in or register to view this page");
                    builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }


            }
        });

        return view;
    }
    private void retrieveCartItemsForUser(String userId) {

        cartRef = FirebaseDatabase.getInstance().getReference("cart");
        cartRef.orderByChild("userID").equalTo(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<CartItem> cartItemList = new ArrayList<>();
                int totalBricks = 0;
                int totalPrice = 0;


                for (DataSnapshot cartItemSnapshot : snapshot.getChildren()) {
                    CartItem cartItem = cartItemSnapshot.getValue(CartItem.class);
                    cartItemList.add(cartItem);

                   totalBricks += cartItem.getQuantity();
                   totalPrice += (cartItem.getPrice());

                }


               updateCheckoutUI(totalBricks, totalPrice);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void updateCheckoutUI(double tTotalBricks, double tTotalPrice) {

        totalBricks.setText(String.valueOf(tTotalBricks));
        totalPrice.setText(String.valueOf(tTotalPrice));
    }

    private void getSavedAdressDB()
    {
        if (user!= null)
        {
            String uid = user.getUid();
            usrRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        String getlocation = dataSnapshot.child(uid).child("location").getValue(String.class);
                        delveryadress.setText("");

                        delveryadress.setText(getlocation );
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getActivity()," no user found",Toast.LENGTH_LONG);
                }
            });
        }

    }
    private void confirmPurchase() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
            cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot cartSnapshot) {
                    CartItem cart = cartSnapshot.getValue(CartItem.class);
                    totalCost =  totalPrice.getText().toString();
                    cartItemid = cart.getItemId();
                    String quntity = totalBricks.getText().toString();
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                            helperClass user1 = userSnapshot.getValue(helperClass.class);
                           String setLocation =  delveryadress.getText().toString();
                                 userid = user.getUid();
                                 deliveryAddress = setLocation;
                                 userEmail = user1.email;
                                 userName = user1.username;
                                 userMobile = user1.phoneNo;
                                    String orderId = confirmedOrdersReference.push().getKey();
                                    confirmedOrder order = new confirmedOrder(quntity,deliveryAddress,totalCost,userId,userEmail,userName,userMobile);
                                    Toast.makeText(getActivity(),"Order confirmed",Toast.LENGTH_LONG).show();
                                    DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference().child("orders").child(orderId);
                                    ordersRef.setValue(order);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle errors
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle errors
                }
            });
        }
    }

}
