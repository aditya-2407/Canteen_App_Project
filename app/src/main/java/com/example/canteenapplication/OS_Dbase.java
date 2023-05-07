package com.example.canteenapplication;

public class OS_Dbase {
    public String Name;
    public String Qty;
    public String OrderID;
    public String Status;
    public String Product_Name;
    public String Time;
    public String ProductID;

    public OS_Dbase(String Name, String Qty, String OrderID, String Status) {
        this.Name = Name;
        this.Qty = Qty;
        this.OrderID = OrderID;
        this.Status = Status;
    }

    public OS_Dbase(String Name, String Qty, String OrderID, String Status, String Product_Name, String Time, String ProductID) {
        this.Name = Name;
        this.Qty = Qty;
        this.OrderID = OrderID;
        this.Status = Status;
        this.Product_Name = Product_Name;
        this.Time = Time;
        this.ProductID = ProductID;
    }
}
