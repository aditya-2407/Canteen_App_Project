package com.example.canteenapplication.DataBases;

public class Cart {
    Integer customerID;
    String cartID;
    Integer productQuantity;
    String productID;

    public Cart() {
    }

    public Cart(String cartID, Integer customerID, String productID, Integer productQuantity) {
        this.cartID = cartID;
        this.customerID = customerID;
        this.productID = productID;
        this.productQuantity = productQuantity;
    }

    public String getCartID() {
        return cartID;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public String getProductID() {
        return productID;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

}
