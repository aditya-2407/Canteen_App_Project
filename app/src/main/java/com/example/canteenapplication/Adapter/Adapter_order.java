package com.example.canteenapplication.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canteenapplication.Customer.OrderFinalise;
import com.example.canteenapplication.DataBases.Cart;
import com.example.canteenapplication.DataBases.Product;
import com.example.canteenapplication.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Objects;

public class Adapter_order extends RecyclerView.Adapter<Adapter_order.ViewHolder>{

    static DatabaseReference databaseCarts;
    static DatabaseReference databaseProducts;
    static String CustomerID = null;

    List<Cart> cart_list;

    public Adapter_order(List<Cart> cart_list) { this.cart_list = cart_list; }

    public void setCustomerID(String customerID) { CustomerID = customerID; }

    @NonNull
    @Override
    public Adapter_order.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_order.ViewHolder holder, int position) {
        databaseCarts = FirebaseDatabase.getInstance().getReference("Carts");
        databaseProducts = FirebaseDatabase.getInstance().getReference("Products");

        String cartID = cart_list.get(position).getCartID();
        String customerID = cart_list.get(position).getCustomerID();
        String productID = cart_list.get(position).getProductID();
        Integer productQuantity = cart_list.get(position).getProductQuantity();

        System.out.println("CartID: " + cartID);
        System.out.println("CustomerID: " + customerID);
        System.out.println("ProductID: " + productID);
        System.out.println("ProductQuantity: " + productQuantity);

        holder.setData(cartID, customerID, productID, productQuantity);
    }

    @Override
    public int getItemCount() { return cart_list.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productNameCart, productTypeCart, productPriceCart, productQuantityCart, servingsOrderedCart, netPriceCart;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameCart = itemView.findViewById(R.id.productNameCart);
            productTypeCart = itemView.findViewById(R.id.productTypeCart);
            productPriceCart = itemView.findViewById(R.id.productPriceCart);
            productQuantityCart = itemView.findViewById(R.id.productQuantityCart);
            servingsOrderedCart = itemView.findViewById(R.id.servingsOrderedCart);
            netPriceCart = itemView.findViewById(R.id.netPriceCart);
        }

        public void setData(String cartID, String customerID, String productID, Integer productQuantity) {

            // Fetch product from databaseProducts where productID = productID
            databaseProducts.addValueEventListener(new com.google.firebase.database.ValueEventListener() {

                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot snapshot) {
                    for (com.google.firebase.database.DataSnapshot postSnapshot : snapshot.getChildren()) {
                        Product product = postSnapshot.getValue(Product.class);
                        assert product != null;
                        if (Objects.equals(product.getId(), productID)) {
                            productNameCart.setText(product.getProduct_Name());
                            productTypeCart.setText(product.getProduct_Type());
                            productPriceCart.setText(product.getProduct_Price());
                            productQuantityCart.setText(product.getProduct_Quantity());
                            servingsOrderedCart.setText(productQuantity.toString());
                            netPriceCart.setText(String.valueOf(Integer.parseInt(product.getProduct_Price()) * productQuantity));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
            // Set product quantity
            // Set total price
        }
    }

}
