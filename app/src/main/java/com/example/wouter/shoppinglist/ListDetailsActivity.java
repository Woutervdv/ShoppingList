package com.example.wouter.shoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class ListDetailsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener , AdapterView.OnClickListener{



    ListView lv;
    Button btnBerwijderLijst;
    ArrayList<HashMap<String, String>> mem = new ArrayList<HashMap<String, String>>();
    List list;
    DBHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DBHandler(getApplicationContext());

        setContentView(R.layout.activity_list_details);
        lv = (ListView)findViewById(R.id.lvShoppingList);
        btnBerwijderLijst = (Button)findViewById(R.id.btnDeleteList);
        btnBerwijderLijst.setOnClickListener(this);
        lv.setOnItemClickListener(this);
        fillListView();
    }






    public void fillListView() {
        list = (List)getIntent().getSerializableExtra("list");


        ArrayList<ItemsInList> items;




                    items = db.getItemsInList(list.get_listId());



                    for (ItemsInList item : items){
                        Product prod = db.getProductInList(item.get_productId());
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("Name", prod.get_name());
                        map.put("Brand", prod.get_brand());
                        mem.add(map);

                    }
                    int resource = R.layout.listview_item;
                    String[] from = {"Name" , "Brand"};
                    int[] to = {R.id.prodNameTextView , R.id.prodBrandTextView };

                    SimpleAdapter adapter = new SimpleAdapter(this, mem,resource, from, to);
                    lv.setAdapter(adapter);
                    Log.d("list filled", "Feed displayed33");






    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mem.remove(position);
        lv.invalidateViews();
        //db.deleteItemInList();
    }

    @Override
    public void onClick(View v) {
        //db.DeleteList(list);
    }
}
