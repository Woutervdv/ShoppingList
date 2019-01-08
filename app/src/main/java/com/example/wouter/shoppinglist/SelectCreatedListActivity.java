package com.example.wouter.shoppinglist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;


import static android.content.ContentValues.TAG;

public class SelectCreatedListActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener{

    private DBHandler db;
    private ArrayList<Product> products;
    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_created_list);

        db = new DBHandler(getApplicationContext());


        lv = (ListView)findViewById(R.id.listShoppingsLists);
        lv.setOnItemClickListener(this);

        ShowLists();
    }


    private void ShowLists(){
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
