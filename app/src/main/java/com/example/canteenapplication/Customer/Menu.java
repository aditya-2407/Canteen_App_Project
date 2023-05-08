package com.example.canteenapplication.Customer;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.canteenapplication.Adapter.Adapter_menu;
import com.example.canteenapplication.DataBases.Product;
import com.example.canteenapplication.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Menu extends AppCompatActivity {

    SearchView searchView;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    static DatabaseReference databaseProducts;
//    Product_Database product_database = new Product_Database(this);
    List<Product> Prod_List;
    Adapter_menu adapter_menu;
     @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch veg, non_veg, snacks, drinks;

     Button proceedToOrder;
//     ArrayList<Integer> selectedItems = new ArrayList<>();
    int total=0;

    public static Context getContext() {
        return getContext();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        String CustomerID = intent.getStringExtra("CustomerID");

        Prod_List = new ArrayList<>();
        databaseProducts = FirebaseDatabase.getInstance().getReference("Products");
        // Fetch all products from databaseProducts and store in Prod_List
        databaseProducts.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot snapshot) {
                Prod_List.clear();
                for (com.google.firebase.database.DataSnapshot postSnapshot : snapshot.getChildren()) {
//                    Product product = postSnapshot.getValue(Product.class);
//                    Prod_List.add(product);
                    String id = postSnapshot.child("id").getValue().toString();
                    String Product_Name = postSnapshot.child("product_Name").getValue().toString();
                    String Product_Price = postSnapshot.child("product_Price").getValue().toString();
                    String Product_Quantity = postSnapshot.child("product_Quantity").getValue().toString();
                    String Product_Type = postSnapshot.child("product_Type").getValue().toString();

                    Product product = new Product(id, Product_Name, Product_Price, Product_Quantity, Product_Type,"https://firebasestorage.googleapis.com/v0/b/canteen-application-555c1.appspot.com/o/user_images%2F-NUrWytUdnnxPbhPG3qv?alt=media&token=2062c847-1774-4415-9d24-21151d016796");

                    Prod_List.add(product);

                }
                adapter_menu = new Adapter_menu(Prod_List);
                recyclerView.setAdapter(adapter_menu);
                adapter_menu.setCustomerID(CustomerID);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        System.out.println(Prod_List.size());

        searchView = findViewById(R.id.searchView2);
        searchView.clearFocus();

        veg = findViewById(R.id.vegSwitch);
        non_veg = findViewById(R.id.nonVegSwitch);
        snacks = findViewById(R.id.snacksSwitch);
        drinks = findViewById(R.id.drinksSwitch);
        proceedToOrder = findViewById(R.id.proceedToOrder);

        proceedToOrder.setOnClickListener(v -> {

            DatabaseReference carts = FirebaseDatabase.getInstance().getReference("Carts");
            // Check if there any order exist in cart for this customer using CustomerID.
            carts.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
                @Override
                public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot snapshot) {
                    for (com.google.firebase.database.DataSnapshot postSnapshot : snapshot.getChildren()) {
                        String CustomerID1 = postSnapshot.child("customerID").getValue().toString();
                        System.out.println(CustomerID1+" "+CustomerID);
                        if (CustomerID1.equals(CustomerID)) {
                            total += 1;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });

            System.out.println(total);

            if (total > 0) {
                Intent intent1 = new Intent(Menu.this, OrderFinalise.class);
                intent1.putExtra("CustomerID", CustomerID);
                startActivity(intent1);
            }
            else {
                Toast.makeText(this, "Please Select the Product!", Toast.LENGTH_SHORT).show();
            }
            total = 0;
        });

//        listen to veg, non-veg, snacks, drinks switches
        veg.setOnCheckedChangeListener((buttonView, isChecked) -> {
            System.out.println(Prod_List.size());
            if (isChecked) {
                List<Product> filterList = new ArrayList<>();
                for (Product product_structure : Prod_List) {
                    if (product_structure.getProduct_Type().equals("Veg")) {
                        filterList.add(product_structure);
                    }
                }
                adapter_menu.set_filter(filterList);
            } else {
                adapter_menu.set_filter(Prod_List);
            }
        });

        non_veg.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                List<Product> filterList = new ArrayList<>();
                for (Product product_structure : Prod_List) {
                    if (product_structure.getProduct_Type().equals("Non-Veg")) {
                        filterList.add(product_structure);
                    }
                }
                adapter_menu.set_filter(filterList);
            } else {
                adapter_menu.set_filter(Prod_List);
            }
        });

        snacks.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                List<Product> filterList = new ArrayList<>();
                for (Product product_structure : Prod_List) {
                    if (product_structure.getProduct_Type().equals("Snack")) {
                        filterList.add(product_structure);
                    }
                }
                adapter_menu.set_filter(filterList);
            } else {
                adapter_menu.set_filter(Prod_List);
            }
        });

        drinks.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                List<Product> filterList = new ArrayList<>();
                for (Product product_structure : Prod_List) {
                    if (product_structure.getProduct_Type().equals("Drink")) {
                        filterList.add(product_structure);
                    }
                }
                adapter_menu.set_filter(filterList);
            } else {
                adapter_menu.set_filter(Prod_List);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getFilter(newText);
                return false;
            }
        });


        recyclerView = findViewById(R.id.menu_recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    private void getFilter(String newText) {
        List<Product> filterList = new ArrayList<>();
        for (Product product_structure : Prod_List) {
            if (product_structure.getProduct_Name().toLowerCase().contains(newText.toLowerCase())) {
                filterList.add(product_structure);
            }
        }
        adapter_menu.set_filter(filterList);
    }
}