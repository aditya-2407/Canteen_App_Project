package com.example.canteenapplication.Customer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.canteenapplication.DataBases.LogIn_Database;
import com.example.canteenapplication.R;

public class Dashboard extends AppCompatActivity {

    Button orderStatus, newOrder, currentMeal, prevOrders, editProfile;
    TextView greeting;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

//        receive CustomerID from intent
        Intent intent1 = getIntent();
        int CustomerID = intent1.getIntExtra("CustomerID", 0);
        Log.d("CustomerID", String.valueOf(CustomerID));

        String name;
        LogIn_Database db = new LogIn_Database(this);
        name = db.getUsername(CustomerID);

        greeting = findViewById(R.id.greeting);
        orderStatus = findViewById(R.id.orderStatus);
        newOrder = findViewById(R.id.newOrder);
        currentMeal = findViewById(R.id.currentMeal);
        prevOrders = findViewById(R.id.prevOrders);
        editProfile = findViewById(R.id.editProfile);

        greeting.setText("Hey, " + name + "!");

        newOrder.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard.this, Menu.class);
            intent.putExtra("CustomerID", CustomerID);
            startActivity(intent);
        });

    }
}
