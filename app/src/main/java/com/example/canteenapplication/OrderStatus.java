package com.example.canteenapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderStatus extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        Intent intent1 = getIntent();
        String CustomerID = intent1.getStringExtra("CustomerID");
        String Cust_Name = intent1.getStringExtra("Cust_Name");

        recyclerView = findViewById(R.id.order_recycler_view);


        ArrayList<String> OrderIDs = new ArrayList<>();
        ArrayList<OS_Dbase> Orders = new ArrayList<>();

        DatabaseReference Cust_ref = FirebaseDatabase.getInstance().getReference("Orders");

        Cust_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Orders.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String CustomerID2 = dataSnapshot.child("CustomerID").getValue().toString();
                    if (CustomerID.equals(CustomerID2)) {
                        String OrderID = dataSnapshot.child("OrderID").getValue().toString();
                        String OrderStatus = dataSnapshot.child("OrderStatus").getValue().toString();
                        String Time = dataSnapshot.child("OrderTime").getValue().toString();

                        OrderIDs.add(OrderID);

                        int TotalPrice = Integer.parseInt(dataSnapshot.child("OrderTotalPrice").getValue().toString());

                        System.out.println(Cust_Name + " " + TotalPrice + " " + OrderID);

                        DatabaseReference Prod_ref = FirebaseDatabase.getInstance().getReference("ProductsInOrders");
                        Prod_ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    String OrderID2 = dataSnapshot.child("orderID").getValue().toString();
//                                    System.out.println("bafjlsdhklf"+OrderID2 + " " + OrderID);
                                    if (OrderID.equals(OrderID2)) {
                                        String ProductID = dataSnapshot.child("productID").getValue().toString();
                                        String Quantity = dataSnapshot.child("productQuantity").getValue().toString();

                                        DatabaseReference Prod_ref2 = FirebaseDatabase.getInstance().getReference("Products");

                                        Prod_ref2.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                                    String ProductID2 = dataSnapshot.child("id").getValue().toString();
//                                                    System.out.println("asfaf: "+ProductID + " " + ProductID2);
                                                    if (ProductID.equals(ProductID2)) {
                                                        String ProductName = dataSnapshot.child("product_Name").getValue().toString();

                                                        String st = "";
                                                        if (OrderStatus.equals("OrderRecorded")) {
                                                            st = "Preparing";
                                                        }
                                                        else {
                                                            st = "Order Prepared";
                                                        }
                                                        System.out.println(Cust_Name + " " + TotalPrice + " " + st + " " + OrderID + " " + ProductName + " " + Time);
                                                        Orders.add(new OS_Dbase(Cust_Name, Quantity, st, OrderID, ProductName, Time));
                                                    }
                                                }
                                                recyclerView = findViewById(R.id.order_recycler_view);
                                                linearLayoutManager = new LinearLayoutManager(OrderStatus.this);
                                                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                                recyclerView.setLayoutManager(linearLayoutManager);

                                                Adapter_OS orderAdapter = new Adapter_OS(Orders);
                                                recyclerView.setAdapter(orderAdapter);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

//                        System.out.println(Cust_Name + " " + TotalPrice + " " + st + " " + OrderID);
//                        Orders.add(new OS_Dbase(Cust_Name, TotalPrice, st, OrderID));
                    }
                }
//                recyclerView = findViewById(R.id.order_recycler_view);
//                linearLayoutManager = new LinearLayoutManager(OrderStatus.this);
//                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//                recyclerView.setLayoutManager(linearLayoutManager);
//
//                Adapter_OS orderAdapter = new Adapter_OS(Orders);
//                recyclerView.setAdapter(orderAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        System.out.println("OrderIDs: " + OrderIDs);


    }


}