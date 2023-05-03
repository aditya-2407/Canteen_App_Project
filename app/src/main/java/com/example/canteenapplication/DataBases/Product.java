package com.example.canteenapplication.DataBases;

import android.widget.EditText;

public class Product {

    String Id;
    String Product_Name, Product_Price, Product_Quantity, Product_Type;

    EditText prod_quantity_fld;
    EditText prod_price_fld;

    public Product(String id, String product_Name, String product_Price, String product_Quantity, String product_Type) {
        Id = id;
        Product_Name = product_Name;
        Product_Price = product_Price;
        Product_Quantity = product_Quantity;
        Product_Type = product_Type;
    }

    public Product() {
    }

    public String getId() {
        System.out.println("Id: " + Id);
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getProduct_Name() {
        return Product_Name;
    }

    public void setProduct_Name(String product_Name) {
        Product_Name = product_Name;
    }

    public String getProduct_Price() {
        return Product_Price;
    }

    public void setProduct_Price(String product_Price) {
        Product_Price = product_Price;
    }

    public String getProduct_Quantity() {
        return Product_Quantity;
    }

    public void setProduct_Quantity(String product_Quantity) {
        Product_Quantity = product_Quantity;
    }

    public String getProduct_Type() {
        return Product_Type;
    }

    public void setProduct_Type(String product_Type) {
        Product_Type = product_Type;
    }

    public void update_qty(){
        if (!prod_quantity_fld.getText().toString().isEmpty()){
            Product_Quantity = prod_quantity_fld.getText().toString();
        }
    }

    public void update_price(){
        if (!prod_price_fld.getText().toString().isEmpty()) {
            Product_Price = prod_price_fld.getText().toString();
        }
    }

}
