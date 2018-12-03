package com.example.wouter.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ProductsActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener{
    private DBHandler db;
    private ListView lv;
    private ArrayList<Product> products;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list_activity);
        lv = (ListView)findViewById(R.id.productsListView);
        lv.setOnItemClickListener(this);

        db = new DBHandler(getApplicationContext());
        ShowDB();
    }

    public void ShowDB(){
        try{
            products = db.getAllProducts();

            ArrayList<HashMap<String, String>> data =
                    new ArrayList<HashMap<String, String>>();
            for (Product prod : products){
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("Name", prod.get_name());
                map.put("Brand", prod.get_brand());
                data.add(map);
            }

            int resource = R.layout.listview_item;
            String[] from = {"Name" , "Brand"};
            int[] to = {R.id.prodNameTextView , R.id.prodBrandTextView };

            SimpleAdapter adapter = new SimpleAdapter(this, data,resource, from, to);
            lv.setAdapter(adapter);
            Log.d("News reader", "Feed displayed");
        }catch (Exception ex){
            Log.d(TAG, "getProduct: Failed ");
        }


    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
