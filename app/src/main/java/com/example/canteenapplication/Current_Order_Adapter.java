package com.example.canteenapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Current_Order_Adapter extends RecyclerView.Adapter<Current_Order_Adapter.ViewHolder> {

    ArrayList<Order_Struct> current_order_models;
    String phone;

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

        String message = "Your order is ready. Please collect it from the canteen.";

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Remove the Item from the RecyclerView
                current_order_models.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, current_order_models.size());

                // Change the status of the order to "Ready"
                DatabaseReference or = FirebaseDatabase.getInstance().getReference("Orders");
                or.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                            if (dataSnapshot.child("OrderID").getValue().toString().equals(Order_ID)) {
                                or.child(Order_ID).child("OrderStatus").setValue("Ready");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



                // Broadcast the Notification to the Customer that his order is ready
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Customers");
                // Get the phone number of the customer
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                            if (dataSnapshot.child("name").getValue().toString().equals(Customer_Name)) {
                                phone = dataSnapshot.child("phone").getValue().toString();
                                System.out.println("Phone: " + phone);



                                PackageManager packageManager = v.getContext().getPackageManager();
                                Intent i = new Intent(Intent.ACTION_VIEW);

                                try {
                                    String url = "https://api.whatsapp.com/send?phone="+ phone +"&text=" + URLEncoder.encode(message, "UTF-8");
                                    i.setPackage("com.whatsapp");
                                    i.setData(Uri.parse(url));
                                    if (i.resolveActivity(packageManager) != null) {
                                        v.getContext().startActivity(i);
                                    }
                                } catch (Exception e){
                                    e.printStackTrace();
                                }

                                try {
                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(phone, null, message, null, null);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

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
