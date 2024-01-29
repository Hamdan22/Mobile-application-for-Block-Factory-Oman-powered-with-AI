package com.example.binsapp;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<CartItem> orderList;

    public void setCartItemList(List<CartItem> orderList) {
        this.orderList = orderList;
        notifyDataSetChanged();
    }

    public OrderAdapter(List<CartItem> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        CartItem order = orderList.get(position);

        // Bind order data to the view
        holder.itemNameTextView.setText(order.getItemName());
        holder.itemQuantityTextView.setText( order.getQuantity().toString());
        holder.itemPriceTextView.setText(order.getPrice().toString());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView itemNameTextView;
        TextView itemQuantityTextView;
        TextView itemPriceTextView;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            itemNameTextView = itemView.findViewById(R.id.itemName);
            itemQuantityTextView = itemView.findViewById(R.id.itemQuntity);
            itemPriceTextView = itemView.findViewById(R.id.itemPrice);
        }
    }
}
