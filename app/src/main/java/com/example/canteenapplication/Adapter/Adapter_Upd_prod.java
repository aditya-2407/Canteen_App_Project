package com.example.canteenapplication.Adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canteenapplication.DataBases.Product;
import com.example.canteenapplication.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Upd_prod extends RecyclerView.Adapter<Adapter_Upd_prod.ViewHolder> {

    private List<Product> update_prod_models;

    private List<Product> new_prod_models_full;


    public Adapter_Upd_prod(List<Product> update_prod_models) {
        this.update_prod_models = update_prod_models;

        new_prod_models_full = new ArrayList<>(update_prod_models);
    }

    @NonNull
    @Override
    public Adapter_Upd_prod.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_update_row, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull Adapter_Upd_prod.ViewHolder holder, int position) {

            String Product_Name = update_prod_models.get(position).getProduct_Name();
            String Product_Price = update_prod_models.get(position).getProduct_Price();
            String Product_Quantity = update_prod_models.get(position).getProduct_Quantity();
            String Product_Type = update_prod_models.get(position).getProduct_Type();

            holder.setData(Product_Name, Product_Price, Product_Quantity, Product_Type);

            EditText prod_quantity_fld = holder.prod_quantity_fld;
            EditText prod_price_fld = holder.prod_price_fld;

            String name = update_prod_models.get(position).getProduct_Name();
            System.out.println("id: " + name);

            System.out.println("Size of new_prod_models_full: " + new_prod_models_full.size());

            if (new_prod_models_full.size() == 0) {
                new_prod_models_full = new ArrayList<>(update_prod_models);
            }

            prod_quantity_fld.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String new_quantity = prod_quantity_fld.getText().toString().trim();

//                    new_prod_models_full.get(id).setProduct_Quantity(new_quantity);
//                    // which position is being updated
//                    Log.d("position", String.valueOf(id));

                    for (int i = 0; i < new_prod_models_full.size(); i++) {
                        if (new_prod_models_full.get(i).getProduct_Name().equals(name)) {
                            new_prod_models_full.get(i).setProduct_Quantity(new_quantity);
                            System.out.println("id: " + name);
                            System.out.println("new_quantity: " + new_quantity);
                        }
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {


                }
            });

            prod_price_fld.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String new_price = prod_price_fld.getText().toString().trim();

//                    new_prod_models_full.get(id).setProduct_Price(new_price);
//                    // which position is being updated
//                    Log.d("position", String.valueOf(id));

                    for (int i = 0; i < new_prod_models_full.size(); i++) {
                        if (new_prod_models_full.get(i).getProduct_Name().equals(name)) {
                            new_prod_models_full.get(i).setProduct_Price(new_price);
                            System.out.println("id: " + name);
                            System.out.println("new_price: " + new_price);
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

    }

    public List<Product> getNew_prod_models_full() {
        return new_prod_models_full;
    }


    @Override
    public int getItemCount() {
        return update_prod_models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView prod_name_text;
        TextView prod_price_text;
        TextView prod_qty_text;
        EditText prod_quantity_fld;
        EditText prod_price_fld;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            prod_name_text = itemView.findViewById(R.id.prod_name_text);
            prod_price_text = itemView.findViewById(R.id.prod_price_text);
            prod_qty_text = itemView.findViewById(R.id.prod_qty_text);
            prod_quantity_fld = itemView.findViewById(R.id.prod_qty_fld);
            prod_price_fld = itemView.findViewById(R.id.prod_price_fld);
        }

        private void setData(String product_Name, String product_Price, String product_Quantity, String product_Type) {

            prod_name_text.setText(product_Name);
            prod_price_text.setText("Price: " + product_Price);
            prod_qty_text.setText("Qty: " + product_Quantity);

        }
    }

    public void getFilter(String newText) {

            List<Product> filteredList = new ArrayList<>();

            for (Product item : update_prod_models) {
                if (item.getProduct_Name().toLowerCase().contains(newText.toLowerCase())) {
                    filteredList.add(item);
                }
            }

            update_prod_models = filteredList;
            notifyDataSetChanged();

    }

    public void set_filter(List<Product> filterList) {
        update_prod_models = new ArrayList<>();
        update_prod_models.addAll(filterList);
        notifyDataSetChanged();
    }
}
