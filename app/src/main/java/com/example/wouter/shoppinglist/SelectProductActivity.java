package com.example.wouter.shoppinglist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SelectProductActivity  extends AppCompatActivity {

    private DBHandler db;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list_activity);

    }

    public void ShowDB(){
        List<Product> products = db.getAllProducts();

        for (Produc : products){

        }
    }




}
