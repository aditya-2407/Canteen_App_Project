package com.example.canteenapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Adapter_OS extends RecyclerView.Adapter<Adapter_OS.ViewHolder> {

    ArrayList<OS_Dbase> orderList;

    public Adapter_OS(ArrayList<OS_Dbase> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.os_view, parent, false);
        return new Adapter_OS.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String cust_name = orderList.get(position).Name;
        String qty = orderList.get(position).Qty;
        String order_id = orderList.get(position).OrderID;
        String status = orderList.get(position).Status;
        String prod_name = orderList.get(position).Product_Name;
        String time = orderList.get(position).Time;

//        holder.setData(cust_name, prod_price, order_id, status);

        holder.setData(cust_name, qty, order_id, status, prod_name, time);

        RatingBar ratingBar = holder.ratingBar;
        Button btn_rate = holder.btn_rate;

        if (status.equals("Preparing....")) {
            ratingBar.setVisibility(View.GONE);
            btn_rate.setVisibility(View.GONE);
        }


        btn_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Rating: for Position: " + position + " is: " + ratingBar.getRating());

                // Change the Status of the Order to "Previous Order"
//                DatabaseReference order = FirebaseDatabase.getInstance().getReference("Orders");
//                order.child(orderList.get(position).OrderID).child("OrderStatus").setValue("Previous Order");


                // Add the Rating to the Product

                DatabaseReference ratings = FirebaseDatabase.getInstance().getReference("Ratings");

                ratings.child(orderList.get(position).ProductID).setValue(new Rating(orderList.get(position).ProductID, (int) ratingBar.getRating(), orderList.get(position).Name));

                // Remove the visibility of the Rating Bar and the Button
                ratingBar.setVisibility(View.GONE);
                btn_rate.setVisibility(View.GONE);

            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView cust_name, prod_price, order_id, status, prod_name, time;

        RatingBar ratingBar;
        Button btn_rate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cust_name = itemView.findViewById(R.id.cust_name);
            prod_name = itemView.findViewById(R.id.prod_name);
            prod_price = itemView.findViewById(R.id.prod_price);
            order_id = itemView.findViewById(R.id.order_id);
            status = itemView.findViewById(R.id.status);
            time = itemView.findViewById(R.id.time);

            ratingBar = itemView.findViewById(R.id.ratingBar);
            btn_rate = itemView.findViewById(R.id.btn_rate);

        }

        public void setData(String cust_name, String qty, String order_id, String status, String prod_name, String time) {

            this.cust_name.setText("Name: "+cust_name);
            this.prod_price.setText("Qty: "+qty);
            this.order_id.setText("ID: "+status);
            this.status.setText("Status: "+order_id);
            this.prod_name.setText("Product: "+prod_name);
            this.time.setText("Time: "+time);

        }

        public void setData(String cust_name, String qty, String order_id, String status) {

            this.cust_name.setText(cust_name);
            this.prod_price.setText(qty);
            this.order_id.setText(order_id);
            this.status.setText(status);


        }
    }
}
