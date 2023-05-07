package com.example.canteenapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canteenapplication.Adapter.Adapter_Upd_prod;
import com.example.canteenapplication.DataBases.Order;
import com.example.canteenapplication.Vendor.Order_Struct;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Current_Order_Adapter extends RecyclerView.Adapter<Current_Order_Adapter.ViewHolder> {

    ArrayList<Order_Struct> current_order_models;

    public Current_Order_Adapter(ArrayList<Order_Struct> orderList) {
        current_order_models = new ArrayList<>(orderList);
    }

    @NonNull
    @Override
    public Current_Order_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_view, parent, false);
        return new Current_Order_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Current_Order_Adapter.ViewHolder holder, int position) {

        String Order_ID = current_order_models.get(position).OrderID;
        String Customer_Name = current_order_models.get(position).getCustomerName();
        String Product_Name = current_order_models.get(position).getProductName();
        int Product_Price = current_order_models.get(position).getProductPrice();
        int Product_Quantity = current_order_models.get(position).getProductQuantity();

        holder.setData(Order_ID, Customer_Name, Product_Name, Product_Price, Product_Quantity);

        Button btn_done = holder.btn_done;

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remove the following order from the list

//                current_order_models.remove(position);
//                notifyItemRemoved(position);
//                notifyItemRangeChanged(position, current_order_models.size());
//
//                // Remove the Following Order from the database
//                DatabaseReference Orders = FirebaseDatabase.getInstance().getReference("Orders");
//                Orders.child(Order_ID).removeValue();
//
//                DatabaseReference ProductsInOrders = FirebaseDatabase.getInstance().getReference("ProductsInOrders");
//                ProductsInOrders.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                            if (dataSnapshot.child("OrderID").getValue().equals(Order_ID)){
//                                ProductsInOrders.child(dataSnapshot.getKey()).removeValue();
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });


                // Broadcast the Notification to the Customer that his order is ready

            }
        });

    }

    @Override
    public int getItemCount() {
        return current_order_models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button btn_done;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        private void setData(String OrderID, String Customer_Name, String Product_Name, int Product_Price, int Product_Quantity) {
            TextView OrderIDView = itemView.findViewById(R.id.order_id);
            TextView CustomerName = itemView.findViewById(R.id.cust_name);
            TextView TotalPrice = itemView.findViewById(R.id.prod_price);

            btn_done = itemView.findViewById(R.id.btn_done);

            OrderIDView.setText(OrderID);
            CustomerName.setText(Customer_Name);
            TotalPrice.setText(String.valueOf(Product_Price));
        }
    }
}
