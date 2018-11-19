package com.example.wouter.shoppinglist;

import android.content.SharedPreferences;

import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class MakeNewListFragment extends Fragment implements  View.OnClickListener, AddProductDialog.AddProductDialogListener {
    private SharedPreferences prefs;
    private SharedPreferences savedValues;
    private Button addProductToDBButton, selectItemButton;
    SQLiteDatabase dtb;
    DBHandler db;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addProductToDBButton = (Button)getActivity().findViewById(R.id.btnAddProductToDB);
        addProductToDBButton.setOnClickListener(v -> {
            AddProductDialog dialog = new AddProductDialog();
            dialog.show(getFragmentManager(), TAG);
        });

        selectItemButton= (Button)getActivity().findViewById(R.id.btnSelectItem);
        selectItemButton.setOnClickListener((v -> {

        }));


        savedValues = PreferenceManager.getDefaultSharedPreferences(getActivity());

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_make_new_list, container, false);





        return view;
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onSaveButtonClick(DialogFragment dialog) {
        //get name
        EditText entname = (EditText) dialog.getDialog().findViewById(R.id.txtName);
        String name = entname.getText().toString();


        if (name != ""){
            Toast.makeText(getActivity().getApplicationContext(), "Enter data again",Toast.LENGTH_LONG);
        }else {
            db.addNewProduct(new Product(name));
            Toast.makeText(getActivity().getApplicationContext(),"Product Added to List", Toast.LENGTH_LONG);
        }
    }
}
