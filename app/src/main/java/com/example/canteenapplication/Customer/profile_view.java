package com.example.canteenapplication.Customer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.canteenapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class profile_view extends AppCompatActivity {

    ImageView im;

    Uri imageUri;

    EditText ed1, ed2, ed3;

    Button update;

    DatabaseReference CustomerRef, checker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        im = findViewById(R.id.user_image);
        ed1 = findViewById(R.id.Username);
        ed2 = findViewById(R.id.Phone);
        ed3 = findViewById(R.id.Password);
        update = findViewById(R.id.updating_button);

        Intent intent1 = getIntent();
        String CustomerID = intent1.getStringExtra("CustomerID");

        CustomerRef = FirebaseDatabase.getInstance().getReference("Customers").child(CustomerID);

        CustomerRef.child("name").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                ed1.setText("Error getting data");
            } else {
                String name = String.valueOf(task.getResult().getValue());
                ed1.setText(name);
            }
        });

        CustomerRef.child("phone").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                ed2.setText("Error getting data");
            } else {
                String phone = String.valueOf(task.getResult().getValue());
                ed2.setText(phone);
            }
        });

        CustomerRef.child("password").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                ed3.setText("Error getting data");
            } else {
                String password = String.valueOf(task.getResult().getValue());
                ed3.setText(password);
            }
        });

        CustomerRef.child("url").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                ed3.setText("Error getting data");
            } else {
                String url = String.valueOf(task.getResult().getValue());

                // get image from url
                Picasso.get().load(url).into(im);
            }
        });

        update.setOnClickListener(v -> {
            String name = ed1.getText().toString();
            String phone = ed2.getText().toString();
            String password = ed3.getText().toString();

            checker = FirebaseDatabase.getInstance().getReference("Customers");

            ArrayList<String> usernames = new ArrayList<>();

            // get all user names from database
            checker.get().addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    ed1.setText("Error getting data");
                } else {
                    for (int i = 0; i < task.getResult().getChildrenCount(); i++) {
                        String username = String.valueOf(task.getResult().child(String.valueOf(i)).child("name").getValue());
                        usernames.add(username);
                    }
                }
            });

            // check if username already exists
            if (usernames.contains(name)) {
                ed1.setError("Username already exists");
                ed1.requestFocus();
                Toast.makeText(this, "Username Already Exists", Toast.LENGTH_SHORT).show();
            }
            else {
                CustomerRef.child("name").setValue(name);
                CustomerRef.child("phone").setValue(phone);
                CustomerRef.child("password").setValue(password);
                Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(profile_view.this, Dashboard.class);
                intent.putExtra("CustomerID", CustomerID);
                startActivity(intent);
            }
        });


    }
}