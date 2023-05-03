package com.example.canteenapplication.DataBases;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Product_Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Product.db";
    public static final String TABLE_NAME = "Product";
    public static final String COL_0 = "ProductID";
    public static final String COL_1 = "Product_Name";
    public static final String COL_2 = "Product_Price";
    public static final String COL_3 = "Product_Quantity";
    public static final String COL_4 = "Product_Type";

    public Product_Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Product (ProductID INTEGER PRIMARY KEY AUTOINCREMENT, Product_Name TEXT, Product_Price TEXT, Product_Quantity TEXT, Product_Type TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Product");
        onCreate(sqLiteDatabase);
    }

    public void insertData(String Product_Name, String Product_Price, String Product_Quantity, String Product_Type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, Product_Name);
        contentValues.put(COL_2, Product_Price);
        contentValues.put(COL_3, Product_Quantity);
        contentValues.put(COL_4, Product_Type);

        db.insert(TABLE_NAME, null, contentValues);
    }

    public List<Product> getAllProducts() {
        List<Product> Prod_List = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Product", null);
        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                Product update_prod_model = new Product();
                update_prod_model.setProduct_Name(cursor.getString(1));
                update_prod_model.setProduct_Price(cursor.getString(2));
                update_prod_model.setProduct_Quantity(cursor.getString(3));
                update_prod_model.setProduct_Type(cursor.getString(4));
//                update_prod_model.setId(i);
                Prod_List.add(update_prod_model);
                i++;
            } while (cursor.moveToNext());
        }
        return Prod_List;
    }

    public List<Product> getVegProducts() {
        List<Product> Prod_List = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Product WHERE Product_Type = 'Veg'", null);
        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                Product update_prod_model = new Product();
                update_prod_model.setProduct_Name(cursor.getString(1));
                update_prod_model.setProduct_Price(cursor.getString(2));
                update_prod_model.setProduct_Quantity(cursor.getString(3));
                update_prod_model.setProduct_Type(cursor.getString(4));
//                update_prod_model.setId(i);
                Prod_List.add(update_prod_model);
                i++;
            } while (cursor.moveToNext());
        }
        return Prod_List;
    }

    public List<Product> getNonVegProducts() {
        List<Product> Prod_List = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Product WHERE Product_Type = 'Non-Veg'", null);
        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                Product update_prod_model = new Product();
                update_prod_model.setProduct_Name(cursor.getString(1));
                update_prod_model.setProduct_Price(cursor.getString(2));
                update_prod_model.setProduct_Quantity(cursor.getString(3));
                update_prod_model.setProduct_Type(cursor.getString(4));
//                update_prod_model.setId(i);
                Prod_List.add(update_prod_model);
                i++;
            } while (cursor.moveToNext());
        }
        return Prod_List;
    }

    public List<Product> getSnacksProducts() {
        List<Product> Prod_List = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Product WHERE Product_Type = 'Snacks'", null);
        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                Product update_prod_model = new Product();
                update_prod_model.setProduct_Name(cursor.getString(1));
                update_prod_model.setProduct_Price(cursor.getString(2));
                update_prod_model.setProduct_Quantity(cursor.getString(3));
                update_prod_model.setProduct_Type(cursor.getString(4));
//                update_prod_model.setId(i);
                Prod_List.add(update_prod_model);
                i++;
            } while (cursor.moveToNext());
        }
        return Prod_List;
    }

    public List<Product> getDrinksProducts() {
        List<Product> Prod_List = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Product WHERE Product_Type = 'Drinks'", null);
        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                Product update_prod_model = new Product();
                update_prod_model.setProduct_Name(cursor.getString(1));
                update_prod_model.setProduct_Price(cursor.getString(2));
                update_prod_model.setProduct_Quantity(cursor.getString(3));
                update_prod_model.setProduct_Type(cursor.getString(4));
//                update_prod_model.setId(i);
                Prod_List.add(update_prod_model);
                i++;
            } while (cursor.moveToNext());
        }
        return Prod_List;
    }

    public void updateProduct(String Product_Name, String Product_Price, String Product_Quantity, String Product_Type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, Product_Name);
        contentValues.put(COL_2, Product_Price);
        contentValues.put(COL_3, Product_Quantity);
        contentValues.put(COL_4, Product_Type);
        db.update(TABLE_NAME, contentValues, "Product_Name = ?", new String[]{Product_Name});
    }

    public Integer getProductID(String Product_Name) {
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Product WHERE Product_Name = ?", new String[]{Product_Name});
        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        return null;
    }

}
