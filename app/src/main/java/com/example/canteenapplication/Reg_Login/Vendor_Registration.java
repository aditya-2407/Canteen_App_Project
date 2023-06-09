package com.example.canteenapplication.Reg_Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.canteenapplication.Customer.user;
import com.example.canteenapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

public class Vendor_Registration extends AppCompatActivity {

    EditText Username_Box;
    EditText Phone_Box;
    EditText Password_Box;
    EditText Confirm_Password_Box;

    DatabaseReference VendorRef;

    StorageReference storageReference;


    Button SignUp_Button;
//    Button Login_Button;
//
//    Button CustomerReg_Button;

    String Username;
    String Phone;
    String Password;
    String Confirm_Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_registration);

        Username_Box = findViewById(R.id.Username);
        Phone_Box = findViewById(R.id.Phone);
        Password_Box = findViewById(R.id.Password);
        Confirm_Password_Box = findViewById(R.id.Confirm_Password);


        SignUp_Button = findViewById(R.id.SignUp);
//        Login_Button = findViewById(R.id.LogIn);
//        CustomerReg_Button = findViewById(R.id.CustomerReg);

        VendorRef = FirebaseDatabase.getInstance().getReference("Vendors");

        SignUp_Button.setOnClickListener(v -> {
            Username = Username_Box.getText().toString();
            Phone = Phone_Box.getText().toString();
            Password = Password_Box.getText().toString();
            Confirm_Password = Confirm_Password_Box.getText().toString();


            if (Username.isEmpty() || Phone.isEmpty() || Password.isEmpty() || Confirm_Password.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            } else if (!Password.equals(Confirm_Password)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                // Insert the data into the database
                String id = VendorRef.push().getKey();
                VendorRef.child(id).setValue(new vendor(id, Username, Phone, Password));
//                db.insertData(Username, Phone, Password, "Vendor");
                Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show();

                // Print the values of the text boxes
                Log.d("Username", Username);
                Log.d("Phone", Phone);

                // Go to the Sign Up Page
                Intent intent = new Intent(this, Log_In.class);
                startActivity(intent);
            }

        });

//        Login_Button.setOnClickListener(v -> {
//            // Go to the Log In Page
//            Intent intent = new Intent(this, Log_In.class);
//            startActivity(intent);
//        });

//        CustomerReg_Button.setOnClickListener(v -> {
//            // Go to the Customer Registration Page
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//        });
    }
}