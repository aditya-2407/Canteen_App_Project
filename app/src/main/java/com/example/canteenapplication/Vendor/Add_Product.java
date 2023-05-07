package com.example.canteenapplication.Vendor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.canteenapplication.Customer.user;
import com.example.canteenapplication.DataBases.Product;
import com.example.canteenapplication.R;
import com.example.canteenapplication.Reg_Login.MainActivity;
import com.example.canteenapplication.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Add_Product extends AppCompatActivity {

    ImageView product_image;

    EditText product_name, product_price, product_quantity;

    Button add_product_button, clear_button, selectimg_btn, clearing_btn;

    Button veg_btn, nveg_btn, drink_btn, snack_btn;

    String Product_Type;
    DatabaseReference ProductRef;

    ActivityMainBinding binding;

    Uri imageUri;

    Bitmap b;

    StorageReference storageReference;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());

        product_image = findViewById(R.id.product_image);
        b = BitmapFactory.decodeResource(getResources(), R.drawable.diet_icon);
        product_image.setImageBitmap(b);

        ProductRef = FirebaseDatabase.getInstance().getReference("Products");


        product_name = findViewById(R.id.product_name);
        product_price = findViewById(R.id.product_price);
        product_quantity = findViewById(R.id.product_quantity);

        veg_btn = findViewById(R.id.veg_btn);
        nveg_btn = findViewById(R.id.nveg_btn);
        drink_btn = findViewById(R.id.drink_btn);
        snack_btn = findViewById(R.id.snack_btn);

        veg_btn.setOnClickListener(v -> {
            Product_Type = "Veg";

//            veg_btn.setBackgroundColor(getResources().getColor(R.color.purple_200));
//
//            nveg_btn.setBackgroundColor(getResources().getColor(R.color.white));
//            drink_btn.setBackgroundColor(getResources().getColor(R.color.white));
//            snack_btn.setBackgroundColor(getResources().getColor(R.color.white));

            Toast.makeText(this, "Veg", Toast.LENGTH_SHORT).show();
        });

        nveg_btn.setOnClickListener(v -> {
            Product_Type = "Non-Veg";

//            nveg_btn.setBackgroundColor(getResources().getColor(R.color.purple_200));
//
//            veg_btn.setBackgroundColor(getResources().getColor(R.color.white));
//            drink_btn.setBackgroundColor(getResources().getColor(R.color.white));
//            snack_btn.setBackgroundColor(getResources().getColor(R.color.white));

            Toast.makeText(this, "Non-Veg", Toast.LENGTH_SHORT).show();
        });

        drink_btn.setOnClickListener(v -> {
            Product_Type = "Drink";

//            drink_btn.setBackgroundColor(getResources().getColor(R.color.purple_200));
//
//            veg_btn.setBackgroundColor(getResources().getColor(R.color.white));
//            nveg_btn.setBackgroundColor(getResources().getColor(R.color.white));
//            snack_btn.setBackgroundColor(getResources().getColor(R.color.white));

            Toast.makeText(this, "Drink", Toast.LENGTH_SHORT).show();
        });

        snack_btn.setOnClickListener(v -> {
            Product_Type = "Snack";

//            snack_btn.setBackgroundColor(getResources().getColor(R.color.purple_200));
//
//            veg_btn.setBackgroundColor(getResources().getColor(R.color.white));
//            nveg_btn.setBackgroundColor(getResources().getColor(R.color.white));
//            drink_btn.setBackgroundColor(getResources().getColor(R.color.white));

            Toast.makeText(this, "Snack", Toast.LENGTH_SHORT).show();
        });

        selectimg_btn = findViewById(R.id.selecting_button);
        clearing_btn = findViewById(R.id.clearing_button);

        selectimg_btn.setOnClickListener(v -> {
            Toast.makeText(this, "Select Image", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 100);
        });

        clearing_btn.setOnClickListener(v -> {
            b = BitmapFactory.decodeResource(getResources(), R.drawable.diet_icon);
            product_image.setImageBitmap(b);
        });





        add_product_button = findViewById(R.id.add_product_button);
        clear_button = findViewById(R.id.clear_button);

        add_product_button.setOnClickListener(v -> {

            String Product_Name = product_name.getText().toString();
            String Product_Price = product_price.getText().toString();
            String Product_Quantity = product_quantity.getText().toString();
            String type = Product_Type;

            if (Product_Name.isEmpty() || Product_Price.isEmpty() || Product_Quantity.isEmpty() || Product_Type.isEmpty()) {
                Toast.makeText(this, "Please Fill all the Fields", Toast.LENGTH_SHORT).show();
                return;
            }


            String id = ProductRef.push().getKey();

            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading File....");
            progressDialog.show();


//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
//            Date now = new Date();
//            String fileName = formatter.format(now);
            storageReference = FirebaseStorage.getInstance().getReference("product_images/"+id);


            storageReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url = uri.toString();

                                    assert id != null;
                                    ProductRef.child(id).setValue(new Product(id, Product_Name, Product_Price, Product_Quantity, type, url));
                                }
                            });

                            product_image.setImageURI(null);
                            Toast.makeText(Add_Product.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {


                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                            Toast.makeText(Add_Product.this, "Failed to upload", Toast.LENGTH_SHORT).show();

                        }
                    });


            Toast.makeText(this, "Product is Successfully Added", Toast.LENGTH_SHORT).show();

            product_name.setText("");
            product_price.setText("");
            product_quantity.setText("");

            b = BitmapFactory.decodeResource(getResources(), R.drawable.diet_icon);
            product_image.setImageBitmap(b);

//            veg_btn.setBackgroundColor(getResources().getColor(R.color.white));
//            nveg_btn.setBackgroundColor(getResources().getColor(R.color.white));
//            drink_btn.setBackgroundColor(getResources().getColor(R.color.white));
//            snack_btn.setBackgroundColor(getResources().getColor(R.color.white));

            Product_Type = null;
        });

        clear_button.setOnClickListener(v -> {

            product_name.setText("");
            product_price.setText("");
            product_quantity.setText("");

//            veg_btn.setBackgroundColor(getResources().getColor(R.color.white));
//            nveg_btn.setBackgroundColor(getResources().getColor(R.color.white));
//            drink_btn.setBackgroundColor(getResources().getColor(R.color.white));
//            snack_btn.setBackgroundColor(getResources().getColor(R.color.white));

            Product_Type = null;

            Toast.makeText(this, "Cleared the Fields", Toast.LENGTH_SHORT).show();

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && data != null && data.getData() != null){

            imageUri = data.getData();
            try {
                b = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            product_image.setImageURI(imageUri);
        }
    }
}