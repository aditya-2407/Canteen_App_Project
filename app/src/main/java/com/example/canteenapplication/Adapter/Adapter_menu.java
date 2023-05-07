package com.example.canteenapplication.Adapter;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canteenapplication.DataBases.Cart;
import com.example.canteenapplication.DataBases.Product;
import com.example.canteenapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Adapter_menu extends RecyclerView.Adapter<Adapter_menu.ViewHolder>{

    List<Product> prod_list;
    static String CustomerID = null;

    static DatabaseReference databaseCarts;
    static DatabaseReference databaseProducts;

    public Adapter_menu(List<Product> prod_list) {
        this.prod_list = prod_list;
    }

    @NonNull
    @Override
    public Adapter_menu.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_menu.ViewHolder holder, int position) {

        databaseCarts = FirebaseDatabase.getInstance().getReference("Carts");
        databaseProducts = FirebaseDatabase.getInstance().getReference("Products");

        String Product_Name = prod_list.get(position).getProduct_Name();
        String Product_Price = prod_list.get(position).getProduct_Price();
        String Product_Quantity = prod_list.get(position).getProduct_Quantity();
        String Product_Type = prod_list.get(position).getProduct_Type();

//        System.out.println("Product Name: " + Product_Name);
//        System.out.println("Product Price: " + Product_Price);
//        System.out.println("Product Quantity: " + Product_Quantity);
//        System.out.println("Product Type: " + Product_Type);

        holder.setData(Product_Name, Product_Price, Product_Quantity);
        holder.loadCart();
    }

    @Override
    public int getItemCount() {
        return prod_list.size();
    }

    public void set_filter(List<Product> filter_list) {
        prod_list = filter_list;
        notifyDataSetChanged();
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView prod_name, prod_price, prod_quantity, qty_item;
        Button add_to_cart, remove_from_cart;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            prod_name = itemView.findViewById(R.id.prod_name);
            prod_price = itemView.findViewById(R.id.prod_price);
            prod_quantity = itemView.findViewById(R.id.prod_qty);
            add_to_cart = itemView.findViewById(R.id.addToCart);
            remove_from_cart = itemView.findViewById(R.id.removeFromCart);
            qty_item = itemView.findViewById(R.id.qty_item);

            add_to_cart.setOnClickListener(v -> {
                int qty = Integer.parseInt(qty_item.getText().toString());
                if(qty == 0){
                    addItemToCart();
                    remove_from_cart.setVisibility(View.VISIBLE);
                }
                else{
                    updateItemInCart(qty + 1);
                }
                qty++;
                qty_item.setText(String.valueOf(qty));
            });

            remove_from_cart.setOnClickListener(v -> {
                int qty = Integer.parseInt(qty_item.getText().toString());
                if (qty == 1){
                    remove_from_cart.setVisibility(View.INVISIBLE);
                    deleteItemFromCart();
                }
                else{
                    updateItemInCart(qty - 1);
                }
                qty--;
                qty_item.setText(String.valueOf(qty));
            });

        }

        public void loadCart(){

            databaseProducts.orderByChild("product_Name").equalTo(prod_name.getText().toString())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Product product = snapshot.getValue(Product.class);
                                assert product != null;
                                String prod_id = product.getId();
                                System.out.println("Product ID: " + prod_id);
                                databaseCarts.orderByChild("productID").equalTo(prod_id)
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                    Cart cart = snapshot.getValue(Cart.class);
                                                    assert cart != null;
                                                    if (cart.getCustomerID().equals(CustomerID)) {
                                                        qty_item.setText(String.valueOf(cart.getProductQuantity()));
                                                        remove_from_cart.setVisibility(View.VISIBLE);
                                                        break;
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                Log.d(TAG, "onCancelled: " + databaseError.getMessage());
                                            }
                                        });
                                break;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d(TAG, "onCancelled: " + databaseError.getMessage());
                        }
                    });

        }

        public void setData(String product_name, String product_price, String product_quantity) {
            prod_name.setText(product_name);
            prod_price.setText(product_price);
            prod_quantity.setText(product_quantity);
        }

        public void addItemToCart(){
            String id = databaseCarts.push().getKey();

            databaseProducts.orderByChild("product_Name").equalTo(prod_name.getText().toString())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Product product = snapshot.getValue(Product.class);
                                assert product != null;
                                String prod_id = product.getId();
                                System.out.println("Product ID: " + prod_id);
                                Cart cart = new Cart(id, CustomerID, prod_id, 1);
                                assert id != null;
                                databaseCarts.child(id).setValue(cart);
                                break;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d(TAG, "onCancelled: " + databaseError.getMessage());
                        }
                    });


            Toast.makeText(itemView.getContext(), "Item added to cart", Toast.LENGTH_SHORT).show();
        }

        public void updateItemInCart(int qty){
            databaseProducts.orderByChild("product_Name").equalTo(prod_name.getText().toString())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Product product = snapshot.getValue(Product.class);
                                assert product != null;
                                String prod_id = product.getId();
                                System.out.println("Product ID: " + prod_id);
                                databaseCarts.orderByChild("productID").equalTo(prod_id)
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                    Cart cart = snapshot.getValue(Cart.class);
                                                    assert cart != null;
                                                    if (cart.getCustomerID().equals(CustomerID)) {
                                                        snapshot.getRef().child("productQuantity").setValue(qty);
                                                        break;
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                Log.d(TAG, "onCancelled: " + databaseError.getMessage());
                                            }
                                        });
                                break;
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d(TAG, "onCancelled: " + databaseError.getMessage());
                        }
                    });
        }

        public void deleteItemFromCart(){
            databaseProducts.orderByChild("product_Name").equalTo(prod_name.getText().toString())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Product product = snapshot.getValue(Product.class);
                                assert product != null;
                                String prod_id = product.getId();
                                System.out.println("Product ID: " + prod_id);
                                databaseCarts.orderByChild("productID").equalTo(prod_id)
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                    Cart cart = snapshot.getValue(Cart.class);
                                                    assert cart != null;
                                                    if (cart.getCustomerID().equals(CustomerID)) {
                                                        snapshot.getRef().removeValue();
                                                        break;
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                Log.d(TAG, "onCancelled: " + databaseError.getMessage());
                                            }
                                        });
                                break;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d(TAG, "onCancelled: " + databaseError.getMessage());
                        }
                    });
        }


    }
}
