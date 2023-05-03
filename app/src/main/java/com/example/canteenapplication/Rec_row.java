package com.example.canteenapplication;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Rec_row extends AppCompatActivity {

    EditText prod_qty_fld;
    EditText prod_price_fld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_row);

        prod_qty_fld = findViewById(R.id.prod_qty_fld);
        prod_price_fld = findViewById(R.id.prod_price_fld);
    }
}
