package com.example.canteenapplication.DataBases;

public class ProductsInOrder {
    String ProductsInOrderID;
    String OrderID;
    String ProductID;
    Integer ProductQuantity;

    public ProductsInOrder() {
    }

    public ProductsInOrder(String ProductsInOrderID, String OrderID, String ProductID, Integer ProductQuantity) {
        this.ProductsInOrderID = ProductsInOrderID;
        this.OrderID = OrderID;
        this.ProductID = ProductID;
        this.ProductQuantity = ProductQuantity;
    }

    public String getProductsInOrderID() {
        return ProductsInOrderID;
    }

    public String getOrderID() {
        return OrderID;
    }

    public String getProductID() {
        return ProductID;
    }

    public Integer getProductQuantity() {
        return ProductQuantity;
    }

}
