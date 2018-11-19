package com.example.wouter.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class MainActivityFragment extends Fragment implements  View.OnClickListener {

    private Button startNewListButton;
    private Button selectCreatedListButton;
    DBHandler db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        db = new DBHandler(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_main_activity, container);
        startNewListButton = (Button)view.findViewById(R.id.btnStartNewList);
        selectCreatedListButton = (Button)view.findViewById(R.id.btnSelectCreatedList);
        startNewListButton.setOnClickListener(this);
        selectCreatedListButton.setOnClickListener(this);
        return view;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnStartNewList:
                makeNewList();
                break;
            case R.id.btnSelectCreatedList:

                break;
        }
    }


    public void makeNewList(){
        Intent intent = new Intent(getActivity() , MakeNewListActivity.class);
        this.startActivity(intent);
    }


}
