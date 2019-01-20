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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class ListDetailsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener , AdapterView.OnClickListener{



    ListView lv;
    Button btnBerwijderLijst, btnSendMessage;
    ArrayList<HashMap<String, String>> mem = new ArrayList<HashMap<String, String>>();
    List list;
    DBHandler db;
    ArrayList<Product> products = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DBHandler(getApplicationContext());

        setContentView(R.layout.activity_list_details);
        lv = (ListView)findViewById(R.id.lvShoppingList);
        btnSendMessage = (Button)findViewById(R.id.btnSendMessage);
        btnSendMessage.setOnClickListener(this);
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
                        products.add(prod);

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
        View v = lv.getChildAt(position);
        Product prod = db.getProductId(((TextView) v.findViewById(R.id.prodNameTextView)).getText().toString() , ((TextView)v.findViewById(R.id.prodBrandTextView)).getText().toString());
        db.deleteItemInList(list.get_listId() , prod.get_id());
        mem.remove(position);
        lv.invalidateViews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSendMessage:
                Intent intent1 = new Intent(this, SMSActivity.class);
                intent1.putExtra("products" , products);
                this.startActivity(intent1);


                break;
            case R.id.btnDeleteList:
                db.DeleteList(list);
                Intent intent2 = new Intent(this, SelectCreatedListActivity.class);
                setResult(RESULT_OK , intent2);
                finish();
                break;
        }

    }
}
