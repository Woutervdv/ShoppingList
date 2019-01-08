package com.example.wouter.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;
import android.widget.SimpleAdapter;

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
    private static final String TABLE_LIST = "list";
    private static final String TABLE_ITEMS_IN_LISTS = "itemsInList";

    //define table columns
    private static final String KEY_ID = "id";
    private static final String KEY_LIST_ID = "listId";
    private static final String KEY_ITEMS_ID = "itemId";
    private static final String KEY_NAME = "name";
    private static final String KEY_LIST_NAME = "listName";
    private static final String KEY_BRAND = "brand";


    private ArrayList<Product> products;

    public DBHandler(Context context) {super(context, DATABASE_NAME, null, DATABASE_VERSION);}



    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCT + "("
                    + KEY_ID + " INTEGER PRIMARY KEY,"
                    + KEY_NAME + " TEXT UNIQUE,"
                    + KEY_BRAND + " TEXT" + ")";
            db.execSQL(CREATE_PRODUCT_TABLE);

            String CREATE_LIST_TABLE = "CREATE TABLE " + TABLE_LIST + "("
                    + KEY_LIST_ID + " INTEGER PRIMARY KEY,"
                    + KEY_LIST_NAME + " TEXT UNIQUE )";
            db.execSQL(CREATE_LIST_TABLE);

            String CREATE_ITEMS_IN_LIST = "CREATE TABLE " + TABLE_ITEMS_IN_LISTS + " ("
                    + KEY_ITEMS_ID + " INTEGER PRIMARY KEY, "
                    + KEY_LIST_ID + " INTEGER, "
                    + KEY_ID + " INTEGER )";
            db.execSQL(CREATE_ITEMS_IN_LIST);
        }catch (Exception ex){
            Log.d(TAG, "onCreate failed");
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
            //drop old table
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIST);
            //create new one
            onCreate(db);
        }catch (Exception ex){
            Log.d(TAG, "onUpdate: failed");
        }


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

    public Product getItem (int index){
        products = getAllProducts();
        return products.get(index);
    }

    public int saveList(String title){
        int id =0 ;
        SQLiteDatabase dbw = this.getWritableDatabase();
        SQLiteDatabase dbr = this.getReadableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put(KEY_LIST_NAME, title);

            //insert row
            dbw.insert(TABLE_LIST, null, values);

            dbw.close();


            String GET_ID = "SELECT "+ KEY_ID + " FROM "+ TABLE_LIST + " WHERE " + KEY_LIST_NAME + " = ?;" ;
            id =(int)DatabaseUtils.longForQuery(dbr,GET_ID , new String[]{ title });
        }catch (Exception ex){
            Log.d(TAG, "saveList: failed");
        }finally {
            dbw.close();
            dbr.close();
            return id;
        }

    }

    public void putItemToList(int id, String name ){
        SQLiteDatabase db = this.getWritableDatabase();
        try{

            ContentValues values = new ContentValues();
            values.put(KEY_LIST_ID, id);
            values.put(KEY_NAME , name);

            //inserting row
            db.insert(TABLE_ITEMS_IN_LISTS , null, values);
        }catch (Exception ex){
            Log.d(TAG, "putItemToList: Failed ");
        }finally {
            db.close();
        }
    }




}
