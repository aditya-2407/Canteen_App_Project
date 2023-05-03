package com.example.canteenapplication.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.canteenapplication.Adapter.Adapter_order;
import com.example.canteenapplication.DataBases.Cart;
import com.example.canteenapplication.DataBases.Product;
import com.example.canteenapplication.Payment;
import com.example.canteenapplication.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderFinalise extends AppCompatActivity {

    DatabaseReference databaseCart;
    List<Cart> Cart_List;
    Adapter_order adapter_order;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    Button changeOrder, headToPayment;
    TextView totalPrice;
    int totalAmount;
    String phonenumber;
    String username;


    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Intent intent = getIntent();
        String CustomerID = intent.getStringExtra("CustomerID");

        changeOrder = findViewById(R.id.changeOrder);
        headToPayment = findViewById(R.id.headToPayment);
        totalPrice = findViewById(R.id.totalPrice);

        // Fetch all carts from databaseCart where CustomerID = CustomerID, get the corresponding product price from databaseProducts using productID from databaseCart and calculate total price
        databaseCart = FirebaseDatabase.getInstance().getReference("Carts");
        databaseCart.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot snapshot) {
                final Integer[] totalPriceInt = {0};
                for (com.google.firebase.database.DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Cart cart = postSnapshot.getValue(Cart.class);
                    assert cart != null;
                    if (Objects.equals(cart.getCustomerID(), CustomerID)) {
                        DatabaseReference databaseProducts = FirebaseDatabase.getInstance().getReference("Products");
                        databaseProducts.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot snapshot) {
                                for (com.google.firebase.database.DataSnapshot postSnapshot : snapshot.getChildren()) {
                                    Product product = postSnapshot.getValue(Product.class);
                                    assert product != null;
                                    if (Objects.equals(product.getId(), cart.getProductID())) {
                                        totalPriceInt[0] += Integer.parseInt(product.getProduct_Price()) * cart.getProductQuantity();
                                        totalPrice.setText("Total Price: " + totalPriceInt[0]);
                                        totalAmount = totalPriceInt[0];
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.e("OrderFinalise", "onCancelled: ", error.toException());
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("OrderFinalise", "onCancelled: ", error.toException());
            }
        });

        Cart_List = new ArrayList<>();
        databaseCart = FirebaseDatabase.getInstance().getReference("Carts");
        // Fetch all carts from databaseCart where CustomerID = CustomerID and store in Cart_List
        databaseCart.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot snapshot) {
                Cart_List.clear();
                for (com.google.firebase.database.DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Cart cart = postSnapshot.getValue(Cart.class);
                    assert cart != null;
                    if (Objects.equals(cart.getCustomerID(), CustomerID)) {
                        Cart_List.add(cart);
                    }
                }

                System.out.println("Cart List Size: " + Cart_List.size());

                adapter_order = new Adapter_order(Cart_List);
                recyclerView.setAdapter(adapter_order);
                adapter_order.setCustomerID(CustomerID);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("OrderFinalise", "onCancelled: ", error.toException());
            }
        });

        recyclerView = findViewById(R.id.orderRecyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        changeOrder.setOnClickListener(v -> {
            Intent intent1 = new Intent(OrderFinalise.this, Menu.class);
            intent1.putExtra("CustomerID", CustomerID);
            startActivity(intent1);
        });


        Intent ipay = new Intent(this,Payment.class);
        headToPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ipay.putExtra("amount",totalAmount);
                ipay.putExtra("CustomerID",CustomerID);
                startActivity(ipay);
            }
        });

    }
}