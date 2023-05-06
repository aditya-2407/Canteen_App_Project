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

//        DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference().child("Orders");
//        DatabaseReference productsInOrderRef = FirebaseDatabase.getInstance().getReference().child("ProductsInOrders");
//        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Products");
//        DatabaseReference customerRef = FirebaseDatabase.getInstance().getReference().child("Customers");
//
////        get all entries from productsInOrder
//        productsInOrderRef.addValueEventListener(new ValueEventListener() {
//            String ProductID = "";
//            String OrderID = "";
//            Integer Quantity = 0;
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    ProductsInOrder productsInOrder = dataSnapshot.getValue(ProductsInOrder.class);
//                    assert productsInOrder != null;
//                    ProductID = productsInOrder.getProductID();
//                    OrderID = productsInOrder.getOrderID();
//                    Quantity = productsInOrder.getProductQuantity();
//                    System.out.println(ProductID + " " + OrderID + " " + Quantity);
//                    Order_Struct order_struct = new Order_Struct();
//
////                    get all entries from orders where orderID = OrderID
//                    orderRef.addValueEventListener(new ValueEventListener() {
//                        String CustomerID;
//                        String OrderStatus;
//                        String OrderDate;
//                        String OrderTime;
////                        String OrderID;
//
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                                Order order = dataSnapshot.getValue(Order.class);
//                                assert order != null;
////                                System.out.println(order.getOrderID() + " " + OrderID);
//                                if (order.getOrderID().equals(OrderID)) {
//                                    CustomerID = order.getCustomerID();
//                                    OrderStatus = order.getOrderStatus();
//                                    OrderDate = order.getOrderDate();
//                                    OrderTime = order.getOrderTime();
//                                    OrderID = order.getOrderID();
//                                }
//                            }
////                            get customerName from customers where customerID = CustomerID
//                            customerRef.addValueEventListener(new ValueEventListener() {
//                                String CustomerName;
//
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                                        user customer = dataSnapshot.getValue(user.class);
//                                        assert customer != null;
//                                        if (customer.getId().equals(CustomerID)) {
//                                            CustomerName = customer.getName();
//                                        }
//                                    }
////                                    orderList.add(new Order_Struct(OrderID, CustomerName, OrderStatus, OrderDate, OrderTime));
//                                    order_struct.OrderID = OrderID;
//                                    order_struct.CustomerName = CustomerName;
////                                    order_struct.OrderStatus = OrderStatus;
////                                    order_struct.OrderDate = OrderDate;
////                                    order_struct.OrderTime = OrderTime;
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError error) {
//
//                                }
//                            });
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//
////                    get all entries from products
//                    productRef.addValueEventListener(new ValueEventListener() {
//                        String ProductName;
//                        String ProductPrice;
//
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                                Product product = dataSnapshot.getValue(Product.class);
//                                assert product != null;
//                                if (product.getId().equals(ProductID)) {
//                                    ProductName = product.getProduct_Name();
//                                    ProductPrice = product.getProduct_Price();
//                                    order_struct.ProductName = ProductName;
//                                    order_struct.ProductPrice = ProductPrice;
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//
//                    orderList.add(order_struct);
//
//                    System.out.println(order_struct.OrderID);
//                    System.out.println(order_struct.CustomerName);
//                    System.out.println(order_struct.ProductName);
//                    System.out.println(order_struct.ProductPrice);
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

//        ArrayList<Order> orders = new ArrayList<>();
//        orderRef = FirebaseDatabase.getInstance().getReference().child("Orders");
//
//        orderRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                orders.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    Order order = dataSnapshot.getValue(Order.class);
//                    orders.add(order);
//                }
//
//                System.out.println(orders.get(0).getOrderID());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

//        ArrayList<Product> Products = new ArrayList<>();
//        orderRef = FirebaseDatabase.getInstance().getReference().child("Products");
//
//        orderRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                Products.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    Product prod = dataSnapshot.getValue(Product.class);
//                    Products.add(prod);
//                }
//                System.out.println(Products.get(0).getProduct_Name());
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

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

                                    orderList.add(order_struct);
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