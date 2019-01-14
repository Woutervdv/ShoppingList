package com.example.wouter.shoppinglist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


import static android.content.ContentValues.TAG;

public class SelectCreatedListActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener , Serializable{

    private DBHandler db;
    private ArrayList<List> lists;
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
            lists = db.getAllLists();

            ArrayList<HashMap<String, String>> data =
                    new ArrayList<HashMap<String, String>>();
            for (List list : lists){
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("ListName", list.get_listName());
                data.add(map);
            }

            int resource = R.layout.listview_item;
            String[] from = {"ListName" , " "};
            int[] to = {R.id.prodNameTextView , R.id.prodBrandTextView };

            SimpleAdapter adapter = new SimpleAdapter(this, data,resource, from, to);
            lv.setAdapter(adapter);
            Log.d("Lists", "Feed displayed");
        }catch (Exception ex){
            Log.d(TAG, "getLists: Failed ");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        List list = db.getList(position);

        Intent intent = new Intent(getApplicationContext(), ListDetailsActivity.class);
        intent.putExtra("list" , list);
        startActivity(intent);
    }
}
