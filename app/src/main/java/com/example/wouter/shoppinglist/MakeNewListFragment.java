package com.example.wouter.shoppinglist;

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
import android.widget.Button;

public class MakeNewListFragment extends Fragment implements  View.OnClickListener  {
    private SharedPreferences prefs;
    private SharedPreferences savedValues;
    private Button addProductToDBButton, selectItemButton;
    SQLiteDatabase dtb;
    DBHandler db;


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
                Intent intentSelectItem = new Intent(getActivity() , SelectProductActivity.class);
                this.startActivity(intentSelectItem);
                break;

        }


    }


}
