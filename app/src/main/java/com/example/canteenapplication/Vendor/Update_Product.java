package com.example.canteenapplication.Vendor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.canteenapplication.Adapter.Adapter_Upd_prod;
import com.example.canteenapplication.DataBases.Product;
import com.example.canteenapplication.DataBases.Product_Database;
import com.example.canteenapplication.R;
//import com.example.canteenapplication.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;

public class Update_Product extends AppCompatActivity {


    SearchView searchView;
    Product_Database product_database = new Product_Database(this);
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    List<Product> Prod_List;
    Adapter_Upd_prod adapter_upd_prod;

    Button add_product;
    Button update_product;

    DatabaseReference ProductRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

//        Prod_List = product_database.getAllProducts();
        Prod_List = new ArrayList<>();
        ProductRef = FirebaseDatabase.getInstance().getReference().child("Products");

        ProductRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Prod_List.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Product product_structure = dataSnapshot.getValue(Product.class);
                    Prod_List.add(product_structure);
                }

                System.out.println(Prod_List.size());

                recyclerView = findViewById(R.id.recyclerView);
                linearLayoutManager = new LinearLayoutManager(Update_Product.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);

                adapter_upd_prod = new Adapter_Upd_prod(Prod_List);
                recyclerView.setAdapter(adapter_upd_prod);
                adapter_upd_prod.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();

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

    }

    private void getFilter(String newText) {
        List<Product> filterList = new ArrayList<>();
        for (Product item : Prod_List) {
            if (item.getProduct_Name().toLowerCase().contains(newText.toLowerCase())) {
                filterList.add(item);
            }
        }

        adapter_upd_prod.set_filter(filterList);

    }
}