package com.example.wouter.shoppinglist;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class SMSActivity extends AppCompatActivity implements View.OnClickListener{

    final int SEND_SMS_PERMISSION_REQUEST_CODE=1;
    Button btnSend;
    EditText txtNumber;
    String smsMessage = "Dit is het boodschappenlijstje, automatisch verstuurd: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        btnSend = (Button)findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
        txtNumber = (EditText)findViewById(R.id.txtPhoneNumber);
        btnSend.setEnabled(false);
        if(checkPermission(Manifest.permission.SEND_SMS)){
            btnSend.setEnabled(true);
        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS} , SEND_SMS_PERMISSION_REQUEST_CODE);
            if(checkPermission(Manifest.permission.SEND_SMS)){
                btnSend.setEnabled(true);
            }
        }
        ArrayList<Product>products ;
        products = (ArrayList<Product>) getIntent().getSerializableExtra("products");
        makeMessage(products);


    }

    public void makeMessage(ArrayList<Product> prods){
        for (Product prod : prods){
            smsMessage +=  prod.get_name() + " merk : "+ prod.get_brand() + ", ";
        }
    }




    public boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }


    @Override
    public void onClick(View v) {
        String phoneNumber = txtNumber.getText().toString();


        if(phoneNumber == null || phoneNumber.length() == 0){
            return;

        }

        if (checkPermission(Manifest.permission.SEND_SMS)){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null , smsMessage , null, null);
            Toast.makeText(this, "Message send!", Toast.LENGTH_SHORT).show();


        }else{
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }



}
