package com.example.canteenapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.canteenapplication.Reg_Login.Log_In;
import com.example.canteenapplication.Reg_Login.MainActivity;
import com.example.canteenapplication.Reg_Login.Vendor_Registration;

public class startingOfApp extends AppCompatActivity {

    Button custOpt,venOpt;
    int choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_of_app);

        custOpt = findViewById(R.id.CustOpt);
        venOpt = findViewById(R.id.venOpt);

        Intent intent1 = new Intent(this, Log_In.class);

        custOpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice = 1;
                intent1.putExtra("choice",choice);
                startActivity(intent1);
            }
        });

        venOpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice = 2;
                intent1.putExtra("choice",choice);
                startActivity(intent1);
            }
        });
    }

}