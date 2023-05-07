package com.example.canteenapplication;

import java.util.ArrayList;

public class Rating {
    public String Id;
    public int rating;

    public String Customer_Name;

    public Rating(String id, int rating, String customer_Name) {
        Id = id;
        this.rating = rating;
        Customer_Name = customer_Name;
    }
}
