package com.example.wouter.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

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
    private static final String KEY_BRAND = "brand";

    public DBHandler(Context context) {super(context, DATABASE_NAME, null, DATABASE_VERSION);}



    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCT + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT ,"
                + KEY_BRAND + " TEXT" + ")";
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
        try{

            ContentValues values = new ContentValues();
            values.put(KEY_NAME, newProd.get_name());
            values.put(KEY_BRAND , newProd.get_brand());

            //inserting row
            db.insert(TABLE_PRODUCT , null, values);
        }catch (Exception ex){
            Log.d(TAG, "CreateProduct: Failed ");
        }finally {
            db.close();
        }


    }

    public ArrayList<Product> getAllProducts(){
        ArrayList<Product> productList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PRODUCT;
        SQLiteDatabase db = this.getReadableDatabase();
        try {



            Cursor cursor = db.rawQuery(selectQuery, null);
            try {

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        Product prod = new Product();
                        prod.set_id(Integer.parseInt(cursor.getString(0)));
                        prod.set_name(cursor.getString(1));
                        prod.set_brand(cursor.getString(2));


                        productList.add(prod);
                    } while (cursor.moveToNext());
                }

            } catch (Exception ex){
                Log.d(TAG, "getProduct: Failed ");
            }finally{
                try { cursor.close(); } catch (Exception ignore) {}
            }

        }catch (Exception ex){
            Log.d(TAG, "getProduct: Failed ");
        }finally {
            try { db.close(); } catch (Exception ignore) {}
        }





        return productList;
    }
}
