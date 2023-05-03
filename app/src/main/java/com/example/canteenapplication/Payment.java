package com.example.canteenapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canteenapplication.Customer.Dashboard;
import com.example.canteenapplication.DataBases.Cart;
import com.example.canteenapplication.DataBases.Order;
import com.example.canteenapplication.DataBases.ProductsInOrder;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Payment extends AppCompatActivity implements PaymentResultListener {
    TextView paymentStatus;
    int totalprice;
    String CustomerID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_payment);

        paymentStatus = findViewById(R.id.paymentStatus);

        Intent getPay = getIntent();
        totalprice = getPay.getIntExtra("amount",0);
        CustomerID = getPay.getStringExtra("CustomerID");

        Checkout checkout  = new Checkout();

        checkout.setKeyID("rzp_test_fUHv3WoqaWrrZg");


        JSONObject options = new JSONObject();
        try {

            options.put("name", "Kapil sharma");
            options.put("currency", "INR");
            options.put("amount", totalprice * 100);
            options.put("prefill.email", "customer@example.com");
            options.put("prefill.contact", "9999999999");
            checkout.open(Payment.this, options);


        } catch (JSONException e) {
            Log.d("PAYMENT_ERROR" , e.getMessage());
        }


    }

    @Override
    public void onPaymentSuccess(String s) {
        Log.d("Payment success",s);
        Toast.makeText(this, "Payment Success..", Toast.LENGTH_SHORT).show();
        paymentStatus.setText(s);

        DatabaseReference databaseCart = FirebaseDatabase.getInstance().getReference("Carts");
        DatabaseReference databaseOrder = FirebaseDatabase.getInstance().getReference("Orders");
        DatabaseReference databaseProductsInOrder = FirebaseDatabase.getInstance().getReference("ProductsInOrders");

        // Add order to databaseOrder
        String orderID = databaseOrder.push().getKey();

        // get today's date
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String today = formatter.format(date);

        // get current time
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm:ss");
        String time = formatter2.format(date);

        Order order = new Order(orderID, CustomerID, "OrderRecorded", today, time, totalprice);
        assert orderID != null;
        databaseOrder.child(orderID).setValue(order, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, @NonNull DatabaseReference ref) {
                if (error != null) {
                    Log.d("ORDER_ERROR", error.getMessage());
                }
            }
        });

        // Fetch all carts from databaseCart where CustomerID = CustomerID
        databaseCart.orderByChild("customerID").equalTo(CustomerID).addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot snapshot) {
                for (com.google.firebase.database.DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Cart cart = dataSnapshot.getValue(Cart.class);
                    String cartID = dataSnapshot.getKey();
                    assert cart != null;
                    String productID = cart.getProductID();
                    Integer productQuantity = cart.getProductQuantity();

                    // Add product to databaseProductsInOrder
                    String productInOrderID = databaseProductsInOrder.push().getKey();
                    ProductsInOrder productInOrder = new ProductsInOrder(productInOrderID, orderID, productID, productQuantity);
                    assert productInOrderID != null;
                    databaseProductsInOrder.child(productInOrderID).setValue(productInOrder, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError error, @NonNull DatabaseReference ref) {
                            if (error != null) {
                                Log.d("PRODUCT_IN_ORDER_ERROR", error.getMessage());
                            }
                        }
                    });

                    // Delete cart from databaseCart
                    assert cartID != null;
                    databaseCart.child(cartID).removeValue(new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError error, @NonNull DatabaseReference ref) {
                            if (error != null) {
                                Log.d("CART_ERROR", error.getMessage());
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("PRODUCT_ERROR", error.getMessage());
            }
        });

        Toast.makeText(this, "Order Placed Successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Payment.this, Dashboard.class);
        intent.putExtra("CustomerID", CustomerID);
        startActivity(intent);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.d("Payment Failed",s);
        Toast.makeText(this, "Payment Failed..", Toast.LENGTH_SHORT).show();
        paymentStatus.setText(s);
    }
}