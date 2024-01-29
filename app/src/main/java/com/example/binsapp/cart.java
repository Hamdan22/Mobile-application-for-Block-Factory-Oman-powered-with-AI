package com.example.binsapp;
import android.app.AlertDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.ArrayList;
import java.util.List;
public class cart extends Fragment {
    private List<CartItem> orderList;
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference().child("cart");
    Button b1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        b1 = view.findViewById(R.id.chekoutBTN);
        recyclerView = view.findViewById(R.id.recyclerViewCart);
        orderList = new ArrayList<>();
        orderAdapter = new OrderAdapter(orderList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(orderAdapter);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!= null)
        {String uId = user.getUid();
            retrieveCartItemsForUser(uId);}
        else
        {AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Not signIn");
            builder.setMessage("Please sign in or register to view this page");
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();}
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new checkout();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layouut, fragment).commit();
            }
        });

        return view;
    }
    private void retrieveCartItemsForUser(String userId) {

        cartRef.orderByChild("userID").equalTo(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<CartItem> cartItemList = new ArrayList<>();
                        for (DataSnapshot cartItemSnapshot : dataSnapshot.getChildren()) {
                            CartItem cartItem = cartItemSnapshot.getValue(CartItem.class);
                            cartItemList.add(cartItem);
                        }
                        orderAdapter.setCartItemList(cartItemList);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle failures
                    }
                });
    }


}

