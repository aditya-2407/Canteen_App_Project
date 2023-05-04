package com.example.canteenapplication.DataBases;

public class Order {
    public String OrderID;
    public String CustomerID;
    public String OrderStatus;
    public String OrderDate;
    public String OrderTime;
    public Integer OrderTotalPrice;

    public Order(){}

    public Order(String OrderID, String CustomerID, String OrderStatus, String OrderDate, String OrderTime, Integer OrderTotalPrice){
        this.OrderID = OrderID;
        this.CustomerID = CustomerID;
        this.OrderStatus = OrderStatus;
        this.OrderDate = OrderDate;
        this.OrderTime = OrderTime;
        this.OrderTotalPrice = OrderTotalPrice;
    }

//    public String getOrderID(){
//        return OrderID;
//    }

//    public String getCustomerID(){
//        return CustomerID;
//    }
//
//    public String getOrderStatus(){
//        return OrderStatus;
//    }
//
//    public String getOrderDate(){
//        return OrderDate;
//    }
//
//    public String getOrderTime(){
//        return OrderTime;
//    }
//
//    public Integer getOrderTotalPrice(){
//        return OrderTotalPrice;
//    }

}
