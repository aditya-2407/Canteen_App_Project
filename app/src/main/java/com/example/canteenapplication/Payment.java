package com.example.canteenapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class Payment extends AppCompatActivity implements PaymentResultListener {
    TextView paymentStatus;

    int totalprice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_payment);

        paymentStatus = findViewById(R.id.paymentStatus);

        Intent getPay = getIntent();
        totalprice = getPay.getIntExtra("amount",0);

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
    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.d("Payment Failed",s);
        Toast.makeText(this, "Payment Failed..", Toast.LENGTH_SHORT).show();
        paymentStatus.setText(s);
    }
}