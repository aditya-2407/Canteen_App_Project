package com.example.canteenapplication.Reg_Login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.canteenapplication.Customer.user;
import com.example.canteenapplication.DataBases.Product;
import com.example.canteenapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText Username_Box;
    EditText Phone_Box;
    EditText Password_Box;
    EditText Confirm_Password_Box;

    DatabaseReference CustomerRef;

    StorageReference ref;
    Button SignUp_Button, select_image, clear_image;

    String Username;
    String Phone;
    String Password;
    String Confirm_Password;

    Uri imageUri;

    ImageView im;

    Bitmap b;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Username_Box = findViewById(R.id.Username);
        Phone_Box = findViewById(R.id.Phone);
        Password_Box = findViewById(R.id.Password);
        Confirm_Password_Box = findViewById(R.id.Confirm_Password);

        im = findViewById(R.id.user_image);
        b = BitmapFactory.decodeResource(getResources(), R.drawable.user_icon);
        im.setImageBitmap(b);


        SignUp_Button = findViewById(R.id.SignUp);
        select_image = findViewById(R.id.selecting_button);
        clear_image = findViewById(R.id.clearing_button);

        CustomerRef = FirebaseDatabase.getInstance().getReference("Customers");
        select_image.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 200);
        });

        clear_image.setOnClickListener(v -> {
            b = BitmapFactory.decodeResource(getResources(), R.drawable.user_icon);
            im.setImageBitmap(b);
        });

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
                String id = CustomerRef.push().getKey();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();
                ref = FirebaseStorage.getInstance().getReference("user_images/"+id);

                ref.putFile(imageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                // get the download url
                                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String url = uri.toString();

                                        CustomerRef.child(id).setValue(new user(id, Username, Phone, Password, url));
                                    }
                                });

                                im.setImageURI(null);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
//                db.insertData(Username, Phone, Password, "Customer");
                Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show();

                // Print the values of the text boxes
                Log.d("Username", Username);
                Log.d("Phone", Phone);

                // Go to the Sign Up Page
                Intent intent = new Intent(MainActivity.this, Log_In.class);
                startActivity(intent);
            }

        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 200 && data != null && data.getData() != null){

            imageUri = data.getData();
            try {
                b = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            im.setImageBitmap(b);
        }
    }
}