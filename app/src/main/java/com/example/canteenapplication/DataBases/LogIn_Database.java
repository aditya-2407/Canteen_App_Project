package com.example.canteenapplication.DataBases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LogIn_Database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "User.db";
    public static final String TABLE_NAME = "User";
    public static final String COL_0 = "CustomerID";
    public static final String COL_1 = "Username";
    public static final String COL_2 = "Phone";
    public static final String COL_3 = "Password";
    public static final String COL_4 = "Customer_or_Vendor";

    public LogIn_Database(Context context) {
        super(context, DATABASE_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE User (CustomerID INTEGER PRIMARY KEY AUTOINCREMENT, Username TEXT, Phone TEXT, Password TEXT, Customer_or_Vendor TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS User");
        onCreate(sqLiteDatabase);
    }

    public void insertData(String Username, String Phone, String Password, String Type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, Username);
        contentValues.put(COL_2, Phone);
        contentValues.put(COL_3, Password);
        contentValues.put(COL_4, Type);

        db.insert(TABLE_NAME, null, contentValues);
    }

    public boolean containsData(String Username, String Password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, Username);
        contentValues.put(COL_3, Password);

        return db.query(TABLE_NAME, null, "Username = ? AND Password = ?", new String[] {Username, Password}, null, null, null).moveToFirst();
    }

    public String getType(String Username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, Username);

        Cursor cursor = db.query(TABLE_NAME, new String[] {COL_4}, "Username = ?", new String[] {Username}, null, null, null);
        cursor.moveToFirst();
        return cursor.getString(0);

    }

    public Integer getCustomerID(String Username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, Username);

        Cursor cursor = db.query(TABLE_NAME, new String[] {COL_0}, "Username = ?", new String[] {Username}, null, null, null);
        cursor.moveToFirst();
        return cursor.getInt(0);

    }

    public void dropTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS User");
        onCreate(db);
    }

    public String getUsername(int CustomerID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_0, CustomerID);

        Cursor cursor = db.query(TABLE_NAME, new String[] {COL_1}, "CustomerID = ?", new String[] {String.valueOf(CustomerID)}, null, null, null);
        cursor.moveToFirst();
        return cursor.getString(0);

    }

}
