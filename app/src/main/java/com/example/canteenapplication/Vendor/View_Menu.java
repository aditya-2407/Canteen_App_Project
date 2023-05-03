package com.example.canteenapplication.Vendor;

import androidx.appcompat.app.AppCompatActivity;

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

import com.example.canteenapplication.Adapter.Adapter_menu;
import com.example.canteenapplication.DataBases.Product;
import com.example.canteenapplication.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;

public class View_Menu extends AppCompatActivity {

    SearchView searchView;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    static DatabaseReference databaseProducts;
    //    Product_Database product_database = new Product_Database(this);
    List<Product> Prod_List;
    Adapter_menu adapter_menu;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch veg, non_veg, snacks, drinks;

    public static Context getContext() {
        return getContext();
    }

    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_menu);

        Intent intent = getIntent();
        Integer CustomerID = intent.getIntExtra("CustomerID", 0);

        Prod_List = new ArrayList<>();
        databaseProducts = FirebaseDatabase.getInstance().getReference("Products");
        // Fetch all products from databaseProducts and store in Prod_List
        databaseProducts.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot snapshot) {
                Prod_List.clear();
                for (com.google.firebase.database.DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Product product = postSnapshot.getValue(Product.class);
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

        searchView = findViewById(R.id.searchView2);
        searchView.clearFocus();

        veg = findViewById(R.id.vegSwitch);
        non_veg = findViewById(R.id.nonVegSwitch);
        snacks = findViewById(R.id.snacksSwitch);
        drinks = findViewById(R.id.drinksSwitch);

        veg.setOnCheckedChangeListener((buttonView, isChecked) -> {
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