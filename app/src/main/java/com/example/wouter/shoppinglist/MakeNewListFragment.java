package com.example.wouter.shoppinglist;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MakeNewListFragment extends Fragment implements  View.OnClickListener  {

    private SharedPreferences savedValues;
    private Button addProductToDBButton, selectItemButton;
    private ListView shoppingList;
    private List<Product> product = new ArrayList<>();
    Product prod ;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedValues = PreferenceManager.getDefaultSharedPreferences(getActivity());
        setHasOptionsMenu(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_make_new_list, container, false);



        addProductToDBButton = (Button)view.findViewById(R.id.btnAddProductToDB);
        addProductToDBButton.setOnClickListener(this);
        selectItemButton = (Button)view.findViewById(R.id.btnSelectItem);
        selectItemButton.setOnClickListener(this);




        shoppingList = (ListView)view.findViewById(R.id.shoppingListListView);
        getProduct();

        return view;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnAddProductToDB:
                Intent intentMakeProduct = new Intent(getActivity() , MakeNewProductActivity.class);
                this.startActivity(intentMakeProduct);
                break;

            case R.id.btnSelectItem:
                Intent intentSelectItem = new Intent(getActivity() , ProductsActivity.class);
                this.startActivity(intentSelectItem);
                break;

        }


    }


    private void getProduct(){
        if (getArguments() != null){
            prod = new Product(getArguments().getString("Name"), getArguments().getString("Brand"));
            product.add(prod);

            ArrayList<HashMap<String, String>> data =
                    new ArrayList<HashMap<String, String>>();
            for (Product prod : product){
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("Name", prod.get_name());
                map.put("Brand", prod.get_brand());
                data.add(map);
            }

            int resource = R.layout.listview_item;
            String[] from = {"Name" , "Brand"};
            int[] to = {R.id.prodNameTextView , R.id.prodBrandTextView };

            SimpleAdapter adapter = new SimpleAdapter(this.getActivity(), data,resource, from, to);
            shoppingList.setAdapter(adapter);


        }
    }





}
