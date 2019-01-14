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
    private static final int DATABASE_VERSION = 2;

    //Database name
    private static final String DATABASE_NAME = "ShoppingsList";

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
                    + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + KEY_NAME + " TEXT UNIQUE,"
                    + KEY_BRAND + " TEXT" + ")";
            db.execSQL(CREATE_PRODUCT_TABLE);

            String CREATE_LIST_TABLE = "CREATE TABLE " + TABLE_LIST + "("
                    + KEY_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + KEY_LIST_NAME + " TEXT UNIQUE )";
            db.execSQL(CREATE_LIST_TABLE);

            String CREATE_ITEMS_IN_LIST = "CREATE TABLE " + TABLE_ITEMS_IN_LISTS + " ("
                    + KEY_ITEMS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_LIST_ID + " INTEGER, "
                    + KEY_ID+ " INTEGER,"
                    +"FOREIGN KEY ("+KEY_LIST_ID+") REFERENCES "+TABLE_LIST +" ("+KEY_LIST_ID+"),"
                    +"FOREIGN KEY ("+KEY_ID+") REFERENCES "+TABLE_PRODUCT +" ("+KEY_ID+") )";

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
                Log.d(TAG, "getAllProduct: Failed ");
            }finally{
                try { cursor.close(); } catch (Exception ignore) {}
            }

        }catch (Exception ex){
            Log.d(TAG, "getAllProduct: Failed ");
        }finally {
            try { db.close(); } catch (Exception ignore) {}
        }





        return productList;
    }

    public Product getProduct (int index){
        products = getAllProducts();
        return products.get(index);
    }

    public Product getProductId (String name , String brand){
        Product prod = new Product();;
        String selectQuery = "SELECT * FROM " + TABLE_PRODUCT + " WHERE " + KEY_NAME + " = '" + name + "' AND " + KEY_BRAND + " = '" + brand + "';" ;
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            Cursor cursor = db.rawQuery(selectQuery, null);
            try {

                // looping through all rows and adding to list
                cursor.moveToFirst();


                        prod.set_id(Integer.parseInt(cursor.getString(0)));
                        prod.set_name(cursor.getString(1));
                        prod.set_brand(cursor.getString(2));





            } catch (Exception ex){
                Log.d(TAG, "getProductId: Failed ");
            }finally{
                try { cursor.close(); } catch (Exception ignore) {}
            }

        }catch (Exception ex){
            Log.d(TAG, "getProductId: Failed ");
        }finally {
            try { db.close(); } catch (Exception ignore) {}
        }
        return prod;
    }

    public com.example.wouter.shoppinglist.List getList(int index){
        ArrayList<com.example.wouter.shoppinglist.List> lists = new ArrayList<>();
        lists = getAllLists();
        return lists.get(index);
    }

    public void saveList(com.example.wouter.shoppinglist.List list){

        SQLiteDatabase db = this.getWritableDatabase();

        try{
            ContentValues values = new ContentValues();
            values.put(KEY_LIST_NAME, list.get_listName());

            //insert row
            db.insert(TABLE_LIST, null, values);


            db.close();




        }catch (Exception ex){
            Log.d(TAG, ex.getMessage());
        }finally {
            db.close();


        }

    }


    public void putItemToList(com.example.wouter.shoppinglist.List list, Product prod){
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            Cursor c = db.rawQuery("SELECT "+ KEY_LIST_ID +" FROM "+ TABLE_LIST+" WHERE "+ KEY_LIST_NAME +" = '" + list.get_listName() + "';", null  );
            c.moveToFirst();
            int id = c.getInt(0);
            ContentValues values = new ContentValues();
            values.put(KEY_LIST_ID, id);
            values.put(KEY_ID , prod.get_id());

            //inserting row
            db.insert(TABLE_ITEMS_IN_LISTS , null, values);
        }catch (Exception ex){
            Log.d(TAG, "putItemToList: Failed ");
        }finally {
            db.close();
        }
    }

    public ArrayList<com.example.wouter.shoppinglist.List> getAllLists(){
        ArrayList<com.example.wouter.shoppinglist.List> lists = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_LIST ;
        SQLiteDatabase db = getReadableDatabase();
        try{
            Cursor cursor = db.rawQuery(selectQuery, null);
            try {

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        com.example.wouter.shoppinglist.List list= new com.example.wouter.shoppinglist.List();
                        list.set_listId(Integer.parseInt(cursor.getString(0)));
                        list.set_listName(cursor.getString(1));
                        lists.add(list);
                    } while (cursor.moveToNext());
                }

            } catch (Exception ex){
                Log.d(TAG, "getList: Failed ");
            }finally{
                try { cursor.close(); } catch (Exception ignore) {}
            }

        }catch (Exception ex){
            Log.d(TAG, "getLists: Failed ");
        }finally {
            try { db.close(); } catch (Exception ignore) {}
        }

        return lists;
    }

    public ArrayList<ItemsInList> getItemsInList(int id){
        ArrayList<ItemsInList> items = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_ITEMS_IN_LISTS + " WHERE " + KEY_LIST_ID + " = " + id ;
        SQLiteDatabase db = this.getReadableDatabase();
        try{
            Cursor cursor = db.rawQuery(selectQuery, null);
            try {

                // looping through all rows and adding to list
                cursor.moveToFirst();
                    do {
                        ItemsInList item = new ItemsInList();
                        String test = cursor.getString(0);
                        item.set_itemsId(32);
                        item.set_listId(Integer.parseInt(cursor.getString(1)));
                        item.set_productId(Integer.parseInt(cursor.getString(2)));


                        items.add(item);
                    } while (cursor.moveToNext());


            } catch (Exception ex){
                Log.d(TAG, "getItemsInList: Failed ");
            }finally{
                try { cursor.close(); } catch (Exception ignore) {}
            }

        }catch (Exception ex){
            Log.d(TAG, "getItemsInList: Failed ");
        }finally {
            try { db.close(); } catch (Exception ignore) {}
        }

        return items;

    }

    public Product getProductInList(int id){
        Product prod = new Product();
        String query = "SELECT * FROM " + TABLE_PRODUCT + " WHERE " +KEY_ID + " = " + id ;
        SQLiteDatabase db = this.getReadableDatabase();
        try {



            Cursor cursor = db.rawQuery(query, null);
            try {

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {


                        prod.set_id(Integer.parseInt(cursor.getString(0)));
                        prod.set_name(cursor.getString(1));
                        prod.set_brand(cursor.getString(2));




                }

            } catch (Exception ex){
                Log.d(TAG, "getProductInList: Failed ");
            }finally{
                try { cursor.close(); } catch (Exception ignore) {}
            }

        }catch (Exception ex){
            Log.d(TAG, "getProductInList: Failed ");
        }finally {
            try { db.close(); } catch (Exception ignore) {}
        }
        return prod;
    }

}


// + " WHERE " + KEY_LIST_ID + " = " + id