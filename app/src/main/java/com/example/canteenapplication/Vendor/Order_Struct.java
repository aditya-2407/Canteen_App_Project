package com.example.canteenapplication.Vendor;

public class Order_Struct {

    public String OrderID;
    public String CustomerName;
    public String ProductName;
    public int ProductPrice;
    int ProductQuantity;

    public Order_Struct(String OrderID, String CustomerName, int ProductPrice){
        this.OrderID = OrderID;
        this.CustomerName = CustomerName;
        this.ProductPrice = ProductPrice;
    }

    public Order_Struct(){}

    public String getCustomerName() {
        return CustomerName;
    }

    public String getProductName() {
        return ProductName;
    }

    public int getProductPrice() {
        return ProductPrice;
    }

    public int getProductQuantity() {
        return ProductQuantity;
    }


}
