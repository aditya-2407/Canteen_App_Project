package com.example.canteenapplication.DataBases;

public class Cart {
    String customerID;
    String cartID;
    Integer productQuantity;
    String productID;

    public Cart() {
    }

    public Cart(String cartID, String customerID, String productID, Integer productQuantity) {
        this.cartID = cartID;
        this.customerID = customerID;
        this.productID = productID;
        this.productQuantity = productQuantity;
    }

    public String getCartID() {
        return cartID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getProductID() {
        return productID;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

}
