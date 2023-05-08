package com.example.canteenapplication.Customer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.canteenapplication.OrderStatus;
import com.example.canteenapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Dashboard extends AppCompatActivity {

    Button orderStatus, newOrder, currentMeal, prevOrders, editProfile, healthgpt;
    TextView greeting;

    String Cust_Name;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Intent intent1 = getIntent();
        String CustomerID = intent1.getStringExtra("CustomerID");
        Log.d("CustomerID", String.valueOf(CustomerID));



        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Customers");
        ref.child(CustomerID).child("name").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                String name = String.valueOf(task.getResult().getValue());
                Cust_Name = name;
                Log.d("firebase", String.valueOf(task.getResult().getValue()));
                greeting.setText("Hey, " + name + "!");
            }
        });

        greeting = findViewById(R.id.greeting);
        orderStatus = findViewById(R.id.orderStatus);
        newOrder = findViewById(R.id.newOrder);
//        currentMeal = findViewById(R.id.currentMeal);
//        prevOrders = findViewById(R.id.prevOrders);
        editProfile = findViewById(R.id.editProfile);
        healthgpt = findViewById(R.id.healthgpt);

        newOrder.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard.this, Menu.class);
            intent.putExtra("CustomerID", CustomerID);
            startActivity(intent);
        });

        healthgpt.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard.this, HealthGPT.class);
            intent.putExtra("CustomerID", CustomerID);
            startActivity(intent);
        });

        orderStatus.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard.this, OrderStatus.class);
            intent.putExtra("CustomerID", CustomerID);
            intent.putExtra("Cust_Name", Cust_Name);
            startActivity(intent);
        });

        editProfile.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard.this, profile_view.class);
            intent.putExtra("CustomerID", CustomerID);
            startActivity(intent);
        });

    }
}
