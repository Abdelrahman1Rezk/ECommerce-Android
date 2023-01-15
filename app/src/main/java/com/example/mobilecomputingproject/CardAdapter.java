package com.example.mobilecomputingproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {

    Context context;
    ArrayList<ProductInCard> productInCardsArrayList;

    public CardAdapter(Context context, ArrayList<ProductInCard> productInCardsArrayList) {
        this.context = context;
        this.productInCardsArrayList = productInCardsArrayList;
    }

    @NonNull
    @Override
    public CardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_recycleview,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter.MyViewHolder holder, int position) {
        ProductInCard prod = productInCardsArrayList.get(position);
        holder.name.setText(prod.name);
        holder.price.setText(prod.price);
        holder.quantity.setText(String.valueOf(prod.quantity));
    }

    @Override
    public int getItemCount() {
        return productInCardsArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView email,name,price,quantity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_recview);
            price = itemView.findViewById(R.id.price_recview);
            quantity = itemView.findViewById(R.id.quantity_recview);
        }
    }
}
