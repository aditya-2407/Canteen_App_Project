package com.example.canteenapplication.Vendor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.canteenapplication.R;

public class Vendor_Side extends AppCompatActivity {

    Button add_product, update_product, view_menu;

    TextView vendor_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_side);

        add_product = findViewById(R.id.add_product);
        update_product = findViewById(R.id.update_product);
        view_menu = findViewById(R.id.view_menu);
        vendor_id = findViewById(R.id.welcome_vendor);

        Intent i1 = getIntent();
        String username = i1.getStringExtra("VendorID");

        vendor_id.setText("Welcome " + username);

        add_product.setOnClickListener(v -> {

            Intent intent = new Intent(Vendor_Side.this, Add_Product.class);
            startActivity(intent);

        });

        update_product.setOnClickListener(v -> {

            Intent intent = new Intent(Vendor_Side.this, Update_Product.class);
            startActivity(intent);

        });

        view_menu.setOnClickListener(v -> {

            Intent intent = new Intent(Vendor_Side.this, View_Menu.class);
            startActivity(intent);

        });
    }
}