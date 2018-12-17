package com.example.wouter.shoppinglist;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MakeNewListActivity extends AppCompatActivity implements  View.OnClickListener, AdapterView.OnItemClickListener{


    private SharedPreferences savedValues;
    private Button addProductToDBButton, selectItemButton, saveListButton;
    private EditText title;
    private ListView lv;
    private static final int value =0;
    private DBHandler db;
    ArrayList<HashMap<String, String>> mem =
            new ArrayList<HashMap<String, String>>();
    Product product;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_new_list);


        savedValues = PreferenceManager.getDefaultSharedPreferences(this);

        //setHasOptionsMenu(true);

        db = new DBHandler(getApplicationContext());

        addProductToDBButton = (Button)findViewById(R.id.btnAddProductToDB);
        addProductToDBButton.setOnClickListener(this);
        selectItemButton = (Button)findViewById(R.id.btnSelectItem);
        selectItemButton.setOnClickListener(this);
        saveListButton = (Button)findViewById(R.id.btnSave);
        saveListButton.setOnClickListener(this);

        lv= (ListView)findViewById(R.id.shoppingListListView);
        lv.setOnItemClickListener(this);


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnAddProductToDB:
                Intent intentMakeProduct = new Intent(this , MakeNewProductActivity.class);
                startActivityForResult(intentMakeProduct , value);
                break;

            case R.id.btnSelectItem:
                Intent intentSelectItem = new Intent(this , ProductsActivity.class);
                startActivityForResult(intentSelectItem ,value);
                break;
            case R.id.btnSave:
                title = (EditText)findViewById(R.id.txtTitle);

                db.saveList(title.getText().toString());
                sendDataToDb(title.getText().toString());
                finish();
                break;

        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case value :
                if (data.hasExtra("name")) {
                    product = new Product(data.getStringExtra("name"), data.getStringExtra("brand"));


                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("Name", product.get_name());
                    map.put("Brand", product.get_brand());
                    mem.add(map);

                    int resource = R.layout.listview_item;
                    String[] from = {"Name", "Brand"};
                    int[] to = {R.id.prodNameTextView, R.id.prodBrandTextView};

                    SimpleAdapter adapter = new SimpleAdapter(this, mem, resource, from, to);
                    lv.setAdapter(adapter);
                }
                break;
            }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       mem.remove(position);
       lv.invalidateViews();

    }




    public void sendDataToDb(String title){
        Product prod;
        View v;
        TextView tv1;
        TextView tv2;
        for (int i = 0; i < lv.getCount(); i++) {
            v = lv.getChildAt(i);
            tv1 = (TextView) v.findViewById(R.id.prodNameTextView);
            tv2 = (TextView) v.findViewById(R.id.prodBrandTextView);
            prod= new Product(tv1.getText().toString(), tv2.getText().toString());
            db.putItemToList(prod, title);
        }



    }
}
