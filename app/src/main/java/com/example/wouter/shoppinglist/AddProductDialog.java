package com.example.wouter.shoppinglist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

public class AddProductDialog extends DialogFragment{

    interface AddProductDialogListener{
        void onSaveButtonClick(DialogFragment dialog);
    }

    AddProductDialogListener addProductListener;
    Context context;


    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        //verify that the host activity implements the callback interface
        try {
            addProductListener = (AddProductDialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement AddProductDialogListener");
        }
    }
    //end

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder((getActivity()));

        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.product_form,null))

                .setPositiveButton("ADD", (dialog, id)-> {
                    addProductListener.onSaveButtonClick(AddProductDialog.this);
                })
                .setNegativeButton("Cancel", (dialog, id) ->{
                    AddProductDialog.this.getDialog().cancel();
                });

        return builder.create();
    }
}
