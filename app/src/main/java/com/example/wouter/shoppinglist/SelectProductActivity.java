package com.example.wouter.shoppinglist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SelectProductActivity  extends AppCompatActivity {

    private DBHandler db;
    private ListView lv;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list_activity);
        lv = findViewById(R.id.productsListView);
        db = new DBHandler(this);
        ShowDB();
    }

    public void ShowDB(){
        List<Product> products = db.getAllProducts();

        ArrayAdapter adapter = new ArrayAdapter(SelectProductActivity.this , android.R.layout.activity_list_item , products);
        lv.setAdapter(adapter);

    }




}
