package com.example.canteenapplication.Reg_Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.canteenapplication.Customer.Dashboard;
import com.example.canteenapplication.Customer.Menu;
import com.example.canteenapplication.Customer.user;
import com.example.canteenapplication.DataBases.LogIn_Database;
import com.example.canteenapplication.R;
import com.example.canteenapplication.Vendor.Vendor_Side;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

public class Log_In extends AppCompatActivity {

    EditText Username_Box;
    EditText Password_Box;

    Button LogIn_Button;
//    Button SignUp_Button;

    Button regUser;

    String Username;
    String Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        Username_Box = findViewById(R.id.Username);
        Password_Box = findViewById(R.id.Password);

        LogIn_Button = findViewById(R.id.LogIn);
//        SignUp_Button = findViewById(R.id.SignUp);
        regUser = findViewById(R.id.regUser);


        Intent getintent = getIntent();
        int choice = getintent.getIntExtra("choice",0);


        Intent intent1 = new Intent(this,MainActivity.class);
        Intent intent2 = new Intent(this,Vendor_Registration.class);
        regUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(choice == 1){
                    startActivity(intent1);
                }
                if(choice == 2){
                    startActivity(intent2);
                }
            }
        });

//        SignUp_Button.setOnClickListener( v -> {
//            // Go to Sign Up Page
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//        });

        LogIn_Button.setOnClickListener( v -> {
            // Check if the user exists in the database

            Username = Username_Box.getText().toString();
            Password = Password_Box.getText().toString();

            if (Username.isEmpty() || Password.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                return;
            }

            DatabaseReference Customref = FirebaseDatabase.getInstance().getReference("Customers");
            DatabaseReference Vendorref = FirebaseDatabase.getInstance().getReference("Vendors");

            LogIn_Database db = new LogIn_Database(this);

            if(choice == 1){
                Customref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean exists = false;
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            user user = dataSnapshot.getValue(user.class);
                            if(user.getName().equals(Username) && user.getPassword().equals(Password)){
                                exists = true;
                                break;
                            }
                        }
                        if(exists){
                            Intent intent = new Intent(Log_In.this, Dashboard.class);
                            intent.putExtra("CustomerID", db.getCustomerID(Username));
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(Log_In.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            if(choice == 2){
                Vendorref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean exists = false;
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            vendor vendor = dataSnapshot.getValue(vendor.class);
                            if(vendor.getName().equals(Username) && vendor.getPassword().equals(Password)){
                                exists = true;
                                break;
                            }
                        }
                        if(exists){
                            Intent intent = new Intent(Log_In.this, Vendor_Side.class);
                            intent.putExtra("VendorID", Username);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(Log_In.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}