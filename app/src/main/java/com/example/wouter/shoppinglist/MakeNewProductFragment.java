package com.example.wouter.shoppinglist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class MakeNewProductFragment extends Fragment implements View.OnClickListener {

    Button createProductButton;
    DBHandler db ;
    EditText name;
    EditText brand;
    Product product;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        db = new DBHandler(getActivity().getApplicationContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_make_new_product, container);

        createProductButton = view.findViewById(R.id.btnCreateProduct);
        createProductButton.setOnClickListener(this);
        name = view.findViewById(R.id.txtName);
        brand = view.findViewById(R.id.txtBrand);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCreateProduct:

                product = new Product(name.getText().toString() , brand.getText().toString());
                CreateProduct();
                Toast.makeText(getActivity(), "product created",
                        Toast.LENGTH_LONG).show();

                MakeNewListFragment ldf = new MakeNewListFragment();
                Bundle args = new Bundle();
                args.putString("Name" , product.get_name());
                args.putString("Brand" , product.get_brand());
                ldf.setArguments(args);
                getFragmentManager().beginTransaction().add(R.id.fragmentMake , ldf).commit();





                //getActivity().finish();
                break;
        }
    }




    private void CreateProduct(){
        try{


            db.addNewProduct(product);


        }catch (Exception ex){
            Log.d(TAG, "CreateProduct: Failed ");
        }
    }

}
