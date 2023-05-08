package com.example.canteenapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.canteenapplication.Adapter.Adapter_Upd_prod;
import com.example.canteenapplication.Customer.user;
import com.example.canteenapplication.DataBases.Order;
import com.example.canteenapplication.DataBases.Product;
import com.example.canteenapplication.DataBases.ProductsInOrder;
import com.example.canteenapplication.Vendor.Order_Struct;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Current_Orders extends AppCompatActivity {

    ArrayList<Order_Struct> orderList;

    RecyclerView recyclerView;

    LinearLayoutManager linearLayoutManager;

    Current_Order_Adapter orderAdapter;

    DatabaseReference orderRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_orders);


        orderList = new ArrayList<>();

        ArrayList<Order_Struct> orderList = new ArrayList<>();

        ArrayList<Order> Orders = new ArrayList<>();
        orderRef = FirebaseDatabase.getInstance().getReference().child("Orders");

        orderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Orders.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String OrderID = dataSnapshot.child("OrderID").getValue().toString();
                    String CustomerID = dataSnapshot.child("CustomerID").getValue().toString();
                    String OrderStatus = dataSnapshot.child("OrderStatus").getValue().toString();
                    String OrderDate = dataSnapshot.child("OrderDate").getValue().toString();
                    String OrderTime = dataSnapshot.child("OrderTime").getValue().toString();
                    int TotalPrice = Integer.parseInt(dataSnapshot.child("OrderTotalPrice").getValue().toString());


                    // Get the Customer Name from using CustomerID from the Customers Table
                    DatabaseReference customerRef = FirebaseDatabase.getInstance().getReference().child("Customers");
                    customerRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            orderList.clear();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String CustomerID2 = dataSnapshot.child("id").getValue().toString();
                                String CustomerName = dataSnapshot.child("name").getValue().toString();
                                if (CustomerID2.equals(CustomerID)) {
                                    Order_Struct order_struct = new Order_Struct();
                                    order_struct.OrderID = OrderID;
                                    order_struct.CustomerName = CustomerName;
                                    order_struct.ProductPrice = TotalPrice;

                                    System.out.println(order_struct.OrderID+" "+order_struct.CustomerName+" "+order_struct.ProductPrice);

                                    if (OrderStatus.equals("OrderRecorded")) {
                                        orderList.add(order_struct);
                                    }
                                }
                            }

                            System.out.println(orderList.size());

                            // Recycler View
                            recyclerView = findViewById(R.id.order_recycler_view);
                            linearLayoutManager = new LinearLayoutManager(Current_Orders.this);
                            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            recyclerView.setLayoutManager(linearLayoutManager);

                            orderAdapter = new Current_Order_Adapter(orderList);
                            recyclerView.setAdapter(orderAdapter);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}