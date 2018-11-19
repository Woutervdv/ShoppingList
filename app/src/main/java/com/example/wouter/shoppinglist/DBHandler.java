package com.example.wouter.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    //static variables
    //Database version
    private static final int DATABASE_VERSION = 1;

    //Database name
    private static final String DATABASE_NAME = "products";

    //contacts table name
    private static final String TABLE_PRODUCT = "product";

    //define table columns
    private static final String KEY_ID = "id";
    private  static final String KEY_NAME = "name";

    public DBHandler(Context context) {super(context, DATABASE_NAME, null, DATABASE_VERSION);}



    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCT + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT " + ")";
        db.execSQL(CREATE_PRODUCT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop old table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);

        //create new one
        onCreate(db);

    }

    void addNewProduct(Product newProd){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, newProd.get_name());

        //inserting row
        db.insert(TABLE_PRODUCT , null, values);
        db.close();
    }

    public List<Product> getAllProducts(){
        List<Product> productList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PRODUCT;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery , null);

        if(cursor.moveToFirst()){
            do{
                Product prod = new Product();
                prod.set_id(Integer.parseInt(cursor.getString(0)));
                prod.set_name(cursor.getString(1));

                productList.add(prod);
            } while (cursor.moveToNext());
        }

        return productList;
    }
}
